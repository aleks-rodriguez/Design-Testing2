
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

import services.FloatService;
import services.ProcessionService;
import domain.Float;

@Controller
@RequestMapping(value = {
	"/float/brotherhood", "/float"
})
public class FloatController extends AbstractController {

	@Autowired
	private FloatService		floatService;

	@Autowired
	private ProcessionService	processionService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listFloats() {
		ModelAndView result;
		result = this.custom(new ModelAndView("float/list"));
		result.addObject("floats", this.floatService.findAll());
		result.addObject("requestURI", "float/list.do");
		return result;
	}

	//List
	@RequestMapping(value = "/listProcession", method = RequestMethod.GET)
	public ModelAndView listFloatsForProcession(@RequestParam final int idProcession) {
		ModelAndView result;
		result = this.custom(new ModelAndView("float/list"));
		result.addObject("floats", this.processionService.findOne(idProcession).getFloats());
		result.addObject("requestURI", "float/listProcession.do");
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idFloat) {
		ModelAndView result;
		Float f;
		f = this.floatService.createFloat();
		result = this.createEditModelAndView(f);
		result.addObject("requestURI", "float/brotherhood/edit.do?idFloat=" + idFloat);
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idFloat, Float f, final BindingResult binding) {
		ModelAndView result;
		f = this.floatService.reconstruct(f, binding);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(f);
			result.addObject("requestURI", "float/brotherhood/edit.do?idFloat=" + idFloat);
		} else
			try {
				this.floatService.save(f);
				result = this.custom(new ModelAndView("redirect:../list.do"));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(f, "float.commit.error");
			}
		return result;
	}
	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idFloat) {
		ModelAndView result;
		Float f;
		f = this.floatService.findOne(idFloat);
		Assert.notNull(f);
		result = this.createEditModelAndView(f);
		result.addObject("requestURI", "float/brotherhood/edit.do?idFloat=" + idFloat);
		return result;
	}
	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result = null;
		final Float f;
		f = this.floatService.findOne(id);
		try {
			this.floatService.delete(f.getId());
			result = this.custom(new ModelAndView("redirect:list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.floatService.findOne(id), "float.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idFloat) {
		final ModelAndView result;
		final Float f;
		f = this.floatService.findOne(idFloat);
		result = this.createEditModelAndView(f);
		result.addObject("requestURI", "float/show.do?idFloat=" + f.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Float f) {
		ModelAndView model;
		model = this.createEditModelAndView(f, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Float f, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(f.getId()));

		result = this.editFormsUrlId("float/edit.do", map, "/float/list.do", this.custom(new ModelAndView("float/edit")));
		result.addObject("float", f);
		result.addObject("message", message);
		return result;
	}

}
