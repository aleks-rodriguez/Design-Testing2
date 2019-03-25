
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.MessageService;
import utilities.Utiles;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Sponsor;
import forms.ActorForm;

@Controller
@RequestMapping(value = {
	"/actor", "/brotherhood", "/member", "/administrator", "/chapter", "/sponsor"
})
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	serviceActor;
	@Autowired
	private MessageService	messageService;


	@RequestMapping(value = "/listBrotherhood", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		ModelAndView result;
		result = this.custom(new ModelAndView("brotherhood/list"));
		result.addObject("brotherhoods", this.serviceActor.findAllBrotherhood());
		result.addObject("requestURI", "brotherhood/listBrotherhood.do");
		return result;
	}
	@RequestMapping(value = "/listChapter", method = RequestMethod.GET)
	public ModelAndView listChapter() {
		ModelAndView result;
		result = this.custom(new ModelAndView("chapter/list"));
		result.addObject("chapters", this.serviceActor.findAllChapters());
		result.addObject("requestURI", "chapter/listChapter.do");
		return result;
	}
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
	@RequestMapping(value = "/createChapter", method = RequestMethod.GET)
	public ModelAndView createChapter() {
		ModelAndView model;
		model = this.createEditModelAndView(this.serviceActor.map(this.serviceActor.createActor(Authority.CHAPTER), Authority.CHAPTER));
		model.addObject("authority", Authority.CHAPTER);
		return model;
	}

	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {
		ModelAndView model;
		model = this.createEditModelAndView(this.serviceActor.map(this.serviceActor.createActor(Authority.SPONSOR), Authority.SPONSOR));
		model.addObject("authority", Authority.SPONSOR);
		return model;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result = null;

		Administrator admin = null;
		Member member = null;
		Brotherhood brotherhood = null;
		Chapter chapter = null;
		Sponsor sponsor = null;

		try {
			String authority = "";
			boolean urls;
			if (actor.getAuthority().equals(Authority.ADMIN)) {
				authority = Authority.ADMIN;
				admin = this.serviceActor.reconstructAdministrator(actor, binding);
			} else if (actor.getAuthority().equals(Authority.BROTHERHOOD)) {
				authority = Authority.BROTHERHOOD;
				brotherhood = this.serviceActor.reconstructBrotherhood(actor, binding);
			} else if (actor.getAuthority().equals(Authority.MEMBER)) {
				authority = Authority.MEMBER;
				member = this.serviceActor.reconstructMember(actor, binding);
			} else if (actor.getAuthority().equals(Authority.CHAPTER)) {
				authority = Authority.CHAPTER;
				chapter = this.serviceActor.reconstructChapter(actor, binding);
			} else if (actor.getAuthority().equals(Authority.SPONSOR)) {
				authority = Authority.SPONSOR;
				sponsor = this.serviceActor.reconstructSponsor(actor, binding);
			}

			if (actor.getAuthority().equals(Authority.BROTHERHOOD))
				if (actor.getPictures().size() > 0)
					urls = Utiles.checkURL(actor.getPictures());
				else
					urls = true;
			else
				urls = true;

			if (urls && actor.getAccount().getPassword().equals(actor.getPassword2()) && actor.getAccount().getPassword() != "" && actor.getPassword2() != "") {
				if (actor.getAuthority().equals(Authority.ADMIN))
					this.serviceActor.save(admin, null, null, null, null);
				else if (actor.getAuthority().equals(Authority.BROTHERHOOD))
					this.serviceActor.save(null, brotherhood, null, null, null);
				else if (actor.getAuthority().equals(Authority.MEMBER))
					this.serviceActor.save(null, null, member, null, null);
				else if (actor.getAuthority().equals(Authority.CHAPTER))
					this.serviceActor.save(null, null, null, chapter, null);
				else if (actor.getAuthority().equals(Authority.SPONSOR))
					this.serviceActor.save(null, null, null, null, sponsor);

				result = new ModelAndView("redirect:../security/login.do");
			} else {

				if (actor.getAuthority().equals(Authority.BROTHERHOOD))
					result = this.createEditModelAndView(actor, "actor.passwordOrUrls");
				else
					result = this.createEditModelAndView(actor, "actor.password");

				result.addObject("authority", authority);
				result.addObject("errors", binding.getAllErrors());
			}

		} catch (final ValidationException e) {
			result = this.createEditModelAndView(actor);
			result.addObject("trace", e.getStackTrace());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(actor, "actor.commit.error");
			result.addObject("trace", oops.getStackTrace());
			result.addObject("oops", oops.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonalData() {
		ModelAndView result;
		final Actor a = this.serviceActor.findByUserAccount();
		final ActorForm form = this.serviceActor.map(a, null);
		result = this.createEditModelAndView(form);
		result.addObject("authority", form.getAuthority());
		return result;
	}

	@RequestMapping(value = "/personalBrotherhood", method = RequestMethod.GET)
	public ModelAndView editPersonalBrotherhood(@RequestParam final int idBrotherhood) {
		ModelAndView result;
		final Actor a = this.serviceActor.findOneBrotherhood(idBrotherhood);
		final ActorForm form = this.serviceActor.map(a, null);
		result = this.createEditModelAndView(form);
		result.addObject("authority", form.getAuthority());
		result.addObject("check", false);
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int id) {
		ModelAndView result;
		final Brotherhood bro = this.serviceActor.findOneBrotherhood(id);
		if (bro != null) {
			result = this.createEditModelAndView(bro);
			result.addObject("check", false);
			result.addObject("spammer", Utiles.checkSpammer(bro));
			result.addObject("polarity", Utiles.homotheticalTransformation(this.messageService.getMessageOutBox(bro.getId())));
		} else {
			final Member m = this.serviceActor.findMemberByUser(id);
			result = this.createEditModelAndView(m);
			result.addObject("spammer", Utiles.checkSpammer(m));
			result.addObject("check", false);
			result.addObject("polarity", Utiles.homotheticalTransformation(this.messageService.getMessageOutBox(m.getId())));
		}
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
		result.addObject("authority", actor.getAuthority());
		result.addObject("check", true);
		result.addObject("message", message);
		return result;

	}

}
