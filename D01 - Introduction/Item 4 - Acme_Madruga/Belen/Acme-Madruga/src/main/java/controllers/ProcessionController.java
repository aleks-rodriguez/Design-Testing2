
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.FinderService;
import services.ProcessionService;
import domain.Brotherhood;
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


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProcession() {
		ModelAndView result;
		result = new ModelAndView("procession/list");
		result.addObject("processions", this.processionService.findAll());
		result.addObject("requestURI", "procession/list.do");
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idProcession) {
		UserAccount user;
		user = LoginService.getPrincipal();

		Brotherhood b;
		b = this.processionService.findBrotherhoodByUser(user.getId());
		ModelAndView result;
		Procession p;
		p = this.processionService.createProcession();
		result = this.createEditModelAndView(p);
		result.addObject("floats", b.getFloats());
		result.addObject("requestURI", "procession/brotherhood/edit.do?idProcession=" + idProcession);
		result.addObject("view", false);
		return result;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idProcession, @ModelAttribute("floats") Procession procession, final BindingResult binding) {
		ModelAndView result;
		System.out.println("Floats:" + procession.getFloats());
		procession = this.processionService.reconstruct(procession, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession);
		else
			try {
				this.processionService.save(procession);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idProcession) {
		ModelAndView result;
		Procession p;
		p = this.processionService.findOne(idProcession);
		result = this.createEditModelAndView(p);
		result.addObject("view", false);
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result = null;
		final Procession p;
		p = this.processionService.findOne(id);
		try {
			this.processionService.delete(p.getId());
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.processionService.findOne(id), "procession.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idProcession) {
		final ModelAndView result;
		final Procession p;
		p = this.processionService.findOne(idProcession);
		result = this.createEditModelAndView(p);
		result.addObject("requestURI", "procession/show.do?idProcession=" + p.getId());
		result.addObject("view", true);
		return result;
	}

	// Finder
	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;
		result = this.custom(new ModelAndView("procession/find"));
		result.addObject("finder", this.serviceFinder.memberFinder());
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView model;
		//		UserAccount user;
		//		user = LoginService.getPrincipal();

		model = this.createEditModelAndView(procession, null);
		Brotherhood b;
		//		b = this.floatService.findBrotherhoodByUser(user.getId());
		b = this.processionService.findBrotherhoodByProcession(procession.getId());
		model.addObject("floats", b.getFloats());
		return model;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		//		UserAccount user;
		//		user = LoginService.getPrincipal();

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(procession.getId()));

		result = this.editFormsUrlId(procession, "procession/brotherhood/edit.do", map, "/procession/list.do", this.custom(new ModelAndView("procession/edit")));
		result.addObject("procession", procession);
		Brotherhood b;
		//		b = this.floatService.findBrotherhoodByUser(user.getId());
		b = this.processionService.findBrotherhoodByProcession(procession.getId());
		result.addObject("floats", b.getFloats());
		result.addObject("message", message);
		return result;
	}
}
