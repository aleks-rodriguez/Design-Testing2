
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationSystemService;
import domain.CustomisationSystem;

@Controller
@RequestMapping("/customisation/administrator")
public class CustomisationSystemController extends AbstractController {

	@Autowired
	private CustomisationSystemService	serviceCustom;


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

		result = new ModelAndView("actor/list");
		result.addObject("actors", this.serviceCustom.findAllNoEnabledActors());
		result.addObject("unban", true);
		result.addObject("requestURI", "customisation/administrator/noenabled.do");
		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView banActor(@RequestParam final int id) {
		ModelAndView result;
		this.serviceCustom.banActor(id);
		result = new ModelAndView("redirect:suspicious.do");
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unBanActor(@RequestParam final int id) {
		ModelAndView result;
		this.serviceCustom.unBanActor(id);
		result = new ModelAndView("redirect:noenabled.do");
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

}
