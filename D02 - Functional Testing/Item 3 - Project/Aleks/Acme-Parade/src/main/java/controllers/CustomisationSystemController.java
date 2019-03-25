
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationSystemService;
import services.SponsorshipService;
import utilities.Utiles;
import domain.CustomisationSystem;
import domain.Sponsorship;

@Controller
@RequestMapping("/customisation/administrator")
public class CustomisationSystemController extends AbstractController {

	@Autowired
	private CustomisationSystemService	serviceCustom;
	@Autowired
	private SponsorshipService			sponsorshipService;


	@RequestMapping(value = "/custom", method = RequestMethod.GET)
	public ModelAndView custom() {

		ModelAndView result;

		result = this.custom(new ModelAndView("custom/edit"));

		result.addObject("customisationSystem", this.serviceCustom.findUnique());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final CustomisationSystem customisationSystem, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(customisationSystem);
		else
			try {
				this.serviceCustom.save(customisationSystem);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customisationSystem, "custom.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/noenabled", method = RequestMethod.GET)
	public ModelAndView listNoEnabledActors() {
		ModelAndView result;

		result = this.custom(new ModelAndView("actor/list"));
		result.addObject("actors", this.serviceCustom.findAllNoEnabledActors());
		result.addObject("unban", true);
		result.addObject("requestURI", "customisation/administrator/noenabled.do");
		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView banActor(@RequestParam final int id) {
		ModelAndView result;
		this.serviceCustom.banActor(id);
		result = this.custom(new ModelAndView("redirect:suspicious.do"));
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unBanActor(@RequestParam final int id) {
		ModelAndView result;
		this.serviceCustom.unBanActor(id);
		result = this.custom(new ModelAndView("redirect:noenabled.do"));
		return result;
	}

	@RequestMapping(value = "/spam", method = RequestMethod.GET)
	public ModelAndView spamActor(@RequestParam final int id) {
		ModelAndView result = null;
		this.serviceCustom.flagSpam(id);
		result = this.custom(new ModelAndView("redirect:../../"));
		return result;

	}

	@RequestMapping(value = "/deactivateSponsorships", method = RequestMethod.GET)
	public ModelAndView deactivate() {
		ModelAndView result;
		Collection<Sponsorship> col;
		col = Utiles.desactiveSponsorships(this.sponsorshipService.findAll());
		this.sponsorshipService.save(col);
		result = this.custom(new ModelAndView("redirect:../../"));

		return result;

	}
	protected ModelAndView createEditModelAndView(final CustomisationSystem customisationSystem) {

		ModelAndView result;
		result = this.createEditModelAndView(customisationSystem, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final CustomisationSystem customisationSystem, final String message) {

		ModelAndView result;
		result = this.custom(new ModelAndView("custom/edit"));
		result.addObject("customisationSystem", customisationSystem);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		result = this.custom(new ModelAndView("custom/list"));
		result.addObject("dashboardActor", this.serviceCustom.dashboardActor());
		result.addObject("dashboardProcession", this.serviceCustom.dashboardProcession());
		result.addObject("dashboardRatio", this.serviceCustom.dashboardRatio());
		result.addObject("marcadorNumerico", this.serviceCustom.marcadorNumerico());
		result.addObject("marcadorNumericoArray", this.serviceCustom.marcadorNumericoArray());
		result.addObject("ratioStatus", this.serviceCustom.ratioStatus());
		result.addObject("countPerArea", this.serviceCustom.countPerArea());
		result.addObject("minmaxArea", this.serviceCustom.minmaxArea());
		result.addObject("largestAndSmallerBro", this.serviceCustom.largestAndSmallerBro());
		return result;

	}
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public ModelAndView chartPosition(@CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;

		result = this.custom(new ModelAndView("custom/chart"));

		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		result.addObject("positions", this.serviceCustom.getPositionsCount(lang));

		return result;
	}

}
