
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import utilities.Utiles;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	serviceActor;


	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView model;
		model = this.createEditModelAndView(this.serviceActor.map(this.serviceActor.createActor(Authority.ADMIN), Authority.ADMIN));
		model.addObject("authority", Authority.ADMIN);
		return model;
	}
	@RequestMapping(value = "/createMember", method = RequestMethod.GET)
	public ModelAndView createMember() {
		ModelAndView model;
		model = this.createEditModelAndView(this.serviceActor.map(this.serviceActor.createActor(Authority.MEMBER), Authority.MEMBER));
		model.addObject("authority", Authority.MEMBER);
		return model;
	}
	@RequestMapping(value = "/createBrotherhood", method = RequestMethod.GET)
	public ModelAndView createBrotherhood() {
		ModelAndView model;
		model = this.createEditModelAndView(this.serviceActor.map(this.serviceActor.createActor(Authority.BROTHERHOOD), Authority.BROTHERHOOD));
		model.addObject("authority", Authority.BROTHERHOOD);
		return model;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result = null;

		Administrator admin = null;
		Member member = null;
		Brotherhood brotherhood = null;
		try {
			if (actor.getAuthority().equals(Authority.ADMIN))
				admin = this.serviceActor.reconstructAdministrator(actor, binding);
			else if (actor.getAuthority().equals(Authority.BROTHERHOOD))
				brotherhood = this.serviceActor.reconstructBrotherhood(actor, binding);
			else if (actor.getAuthority().equals(Authority.MEMBER))
				member = this.serviceActor.reconstructMember(actor, binding);

			if (actor.getAccount().getPassword().equals(actor.getPassword2())) {
				if (actor.getAuthority().equals(Authority.ADMIN))
					this.serviceActor.save(admin, null, null);
				else if (actor.getAuthority().equals(Authority.BROTHERHOOD))
					this.serviceActor.save(null, brotherhood, null);
				else if (actor.getAuthority().equals(Authority.MEMBER))
					this.serviceActor.save(null, null, member);

				result = new ModelAndView("redirect:../security/login.do");
			} else {
				result = this.createEditModelAndView(actor, "actor.password");
				result.addObject("errors", binding.getAllErrors());
			}

		} catch (final ValidationException e) {
			result = this.createEditModelAndView(actor);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(actor, "actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonalData() {
		ModelAndView result;
		final Actor a = this.serviceActor.findByUserAccount();
		result = this.createEditModelAndView(this.serviceActor.map(a, null));

		return result;
	}
	protected <T extends Actor> ModelAndView createEditModelAndView(final T actor) {
		ModelAndView model;
		model = this.createEditModelAndView(actor, null);
		return model;
	}
	protected <T extends Actor> ModelAndView createEditModelAndView(final T actor, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("actor/edit"));

		result.addObject("actor", actor);

		result.addObject("message", message);
		return result;

	}
	protected ModelAndView createEditModelAndView(final ActorForm actor) {
		ModelAndView model;
		model = this.createEditModelAndView(actor, null);
		return model;
	}
	protected ModelAndView createEditModelAndView(final ActorForm actor, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("actor/edit"));
		result.addObject("prefix", Utiles.phonePrefix);
		result.addObject("actor", actor);
		result.addObject("message", message);
		return result;

	}

}
