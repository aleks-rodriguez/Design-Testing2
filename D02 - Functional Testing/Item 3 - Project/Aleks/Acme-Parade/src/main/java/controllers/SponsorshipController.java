
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ParadeService;
import services.SponsorshipService;
import utilities.Utiles;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping(value = {
	"/sponsorship/sponsor"
})
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	serviceSponsorship;

	@Autowired
	private ParadeService		serviceParade;

	@Autowired
	private ActorService		serviceActor;


	@RequestMapping(value = "/listSponsorship", method = RequestMethod.GET)
	public ModelAndView listSponsorship() {
		ModelAndView result;
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", this.serviceSponsorship.getSponsorshipBySponsor(this.serviceActor.findSponsorByUserAccount(LoginService.getPrincipal().getId()).getId()));
		result.addObject("requestURI", "sponsorship/sponsor/listSponsorship.do");
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		result = new ModelAndView("sponsorship/edit");
		result = this.createEditModelAndView(this.serviceSponsorship.findOne(id));
		result.addObject("view", true);
		return result;
	}

	@RequestMapping(value = "/ListNoSponsorshipParade", method = RequestMethod.GET)
	public ModelAndView ListNoSponsorParade() {
		ModelAndView result;
		result = new ModelAndView("parade/list");
		Collection<Parade> withoutSponsorshipParades;
		withoutSponsorshipParades = this.serviceParade.findParadesAFM();
		withoutSponsorshipParades.removeAll(this.serviceSponsorship.getParadesBySponsor(this.serviceActor.findSponsorByUserAccount(LoginService.getPrincipal().getId()).getId()));
		result.addObject("parades", withoutSponsorshipParades);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result;
		this.serviceSponsorship.delete(id);
		result = new ModelAndView("redirect:listSponsorship.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int paradeId) {
		ModelAndView result;
		final Sponsor s = this.serviceActor.findSponsorByUserAccount(LoginService.getPrincipal().getId());
		final Parade p = this.serviceParade.findOne(paradeId);
		result = this.createEditModelAndView(this.serviceSponsorship.createSponsorship(s, p));
		result.addObject("view", false);
		result.addObject("requestURI", "sponsorship/sponsor/edit.do?paradeId=" + paradeId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int paradeId, Sponsorship s, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(s);
			result.addObject("requestURI", "sponsorship/sponsor/edit.do?id=" + paradeId);
		} else
			try {
				s = this.serviceSponsorship.reconstruct(s, binding, paradeId);
				this.serviceSponsorship.save(s);
				result = this.custom(new ModelAndView("redirect:listSponsorship.do"));
			} catch (final ValidationException e) {
				result = this.createEditModelAndView(s, "sponsorship.commit.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(s, "sponsorship.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int id) {
		ModelAndView result;
		Sponsorship s;
		s = this.serviceSponsorship.findOne(id);
		final int paradeId = s.getParade().getId();
		result = this.createEditModelAndView(s);
		result.addObject("view", false);
		result.addObject("requestURI", "sponsorship/sponsor/edit.do?paradeId=" + paradeId);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;
		result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);
		result.addObject("makes", Utiles.creditCardMakes());
		return result;
	}
}
