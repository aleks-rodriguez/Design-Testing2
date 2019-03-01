
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.AreaService;
import services.FinderService;
import services.ProcessionService;
import utilities.Utiles;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping(value = {
	"/procession/member", "/procession/brotherhood", "/procession"
})
public class ProcessionController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private FinderService		serviceFinder;

	@Autowired
	private AreaService			serviceArea;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProcession() {
		ModelAndView result;
		result = this.custom(new ModelAndView("procession/list"));
		result.addObject("processions", this.processionService.findAll());
		result.addObject("requestURI", "procession/list.do");
		return result;
	}

	// Finder
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;
		final Member m = (Member) this.serviceFinder.findByUserAccount(LoginService.getPrincipal().getId());
		result = this.createEditModelAndView(this.serviceFinder.findOne(m.getFinder().getId()));
		return result;
	}
	@RequestMapping(value = "/finder", method = RequestMethod.POST, params = "search")
	public ModelAndView search(Finder finder, final BindingResult binding) {

		ModelAndView result;
		finder = this.serviceFinder.reconstruct(finder, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {

				Finder aux;
				aux = this.serviceFinder.save(finder);
				result = this.custom(new ModelAndView("procession/list"));
				result.addObject("requestURI", "procession/member/searchList.do?id=" + aux.getId());
				result.addObject("processions", aux.getProcessions());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam final int id) {
		ModelAndView result;
		result = this.custom(new ModelAndView("procession/list"));

		final Actor a = this.serviceFinder.findByUserAccount(LoginService.getPrincipal().getId());
		final Finder param = this.serviceFinder.findOne(id);

		if (Utiles.findAuthority(a.getAccount().getAuthorities(), Authority.MEMBER) && ((Member) a).getFinder().getId() == id) {
			result.addObject("processions", param.getProcessions());
			result.addObject("requestURI", "procession/list.do?id=" + id);
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView model;
		model = this.createEditModelAndView(finder, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;
		result = this.editFormsUrlId("procession/member/finder.do", new HashMap<String, String>(), "/procession/member/list.do", this.custom(new ModelAndView("procession/find")));
		result.addObject("areas", this.serviceArea.findAll());
		result.addObject("finder", finder);
		result.addObject("message", message);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView model;
		model = this.createEditModelAndView(procession, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(procession.getId()));

		result = this.editFormsUrlId("procession/edit.do", map, "/procession/list.do", this.custom(new ModelAndView("procession/edit")));
		result.addObject("procession", procession);
		result.addObject("message", message);
		return result;
	}
}
