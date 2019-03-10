
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AreaService;
import services.FinderService;
import services.MessageService;
import services.ParadeService;
import utilities.Utiles;
import domain.Actor;
import domain.Brotherhood;
import domain.Finder;
import domain.Member;
import domain.Parade;

@Controller
@RequestMapping(value = {
	"/parade/member", "/parade/brotherhood", "/parade"
})
public class ParadeController extends AbstractController {

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private FinderService	serviceFinder;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private AreaService		serviceArea;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listParades(@RequestParam(defaultValue = "0") final int idBrotherhood) {
		ModelAndView result = null;
		Brotherhood b;
		if (idBrotherhood == 0) {
			UserAccount user;
			user = LoginService.getPrincipal();

			b = this.paradeService.findBrotherhoodByUser(user.getId());
			result = this.custom(new ModelAndView("parade/list"));
		} else {
			result = this.custom(new ModelAndView("parade/list"));
			b = this.actorService.findOneBrotherhood(idBrotherhood);

		}
		result.addObject("parades", b.getParades());
		result.addObject("requestURI", "parade/list.do");
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idParade) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		Assert.isTrue(b.getArea() != null, "A brotherhood must have an area selected");
		ModelAndView result;
		Parade p;
		p = this.paradeService.createParade();
		//		this.messageService.createMessageNotifyCreateProcession(b, p);
		result = this.createEditModelAndView(p);
		result.addObject("floats", b.getFloats());
		result.addObject("requestURI", "parade/brotherhood/edit.do?idParade=" + idParade);
		result.addObject("view", false);
		return result;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idParade, Parade parade, final BindingResult binding) {
		ModelAndView result;
		System.out.println("Floats:" + parade.getFloats());
		parade = this.paradeService.reconstruct(parade, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else
			try {

				this.paradeService.save(parade);
				result = this.custom(new ModelAndView("redirect:../list.do"));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "procession.commit.error");
			}
		return result;
	}
	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idParade) {
		ModelAndView result;
		Parade p;
		p = this.paradeService.findOne(idParade);
		result = this.createEditModelAndView(p);
		result.addObject("view", false);
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result = null;
		final Parade p;
		p = this.paradeService.findOne(id);
		try {
			this.paradeService.delete(p.getId());
			result = this.custom(new ModelAndView("redirect:list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.paradeService.findOne(id), "procession.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idParade) {
		final ModelAndView result;
		final Parade p;
		p = this.paradeService.findOne(idParade);
		result = this.createEditModelAndView(p);
		result.addObject("requestURI", "parade/show.do?idParade=" + p.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView model;

		model = this.createEditModelAndView(parade, null);
		Brotherhood b;
		if (parade.getId() == 0) {
			UserAccount user;
			user = LoginService.getPrincipal();
			b = this.paradeService.findBrotherhoodByUser(user.getId());
		} else
			b = this.paradeService.findBrotherhoodByParade(parade.getId());
		model.addObject("floats", b.getFloats());
		return model;
	}
	protected ModelAndView createEditModelAndView(final Parade parade, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(parade.getId()));

		result = this.editFormsUrlId("parade/brotherhood/edit.do", map, "/parade/list.do", this.custom(new ModelAndView("parade/edit")));
		result.addObject("parade", parade);
		Brotherhood b;
		if (parade.getId() == 0) {
			UserAccount user;
			user = LoginService.getPrincipal();
			b = this.paradeService.findBrotherhoodByUser(user.getId());
		} else
			b = this.paradeService.findBrotherhoodByParade(parade.getId());
		result.addObject("floats", b.getFloats());
		result.addObject("message", message);
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
				result = this.custom(new ModelAndView("parade/list"));
				result.addObject("requestURI", "parade/member/searchList.do?id=" + aux.getId());
				result.addObject("processions", aux.getParades());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam final int id) {
		ModelAndView result;
		result = this.custom(new ModelAndView("parade/list"));

		final Actor a = this.serviceFinder.findByUserAccount(LoginService.getPrincipal().getId());
		final Finder param = this.serviceFinder.findOne(id);

		if (Utiles.findAuthority(a.getAccount().getAuthorities(), Authority.MEMBER) && ((Member) a).getFinder().getId() == id) {
			result.addObject("parades", param.getParades());
			result.addObject("requestURI", "parade/list.do?id=" + id);
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
		result = this.editFormsUrlId("parade/member/finder.do", new HashMap<String, String>(), "/parade/member/list.do", this.custom(new ModelAndView("parade/find")));
		result.addObject("areas", this.serviceArea.findAll());
		result.addObject("finder", finder);
		result.addObject("message", message);
		return result;
	}
}
