
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
