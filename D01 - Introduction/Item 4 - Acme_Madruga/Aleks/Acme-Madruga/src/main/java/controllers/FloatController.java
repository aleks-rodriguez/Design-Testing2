
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FloatService;
import domain.Float;

@Controller
@RequestMapping(value = {
	"/float/brotherhood", "/float"
})
public class FloatController extends AbstractController {

	@Autowired
	private FloatService	floatService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listFloats() {
		ModelAndView result;
		result = new ModelAndView("float/list");
		result.addObject("floats", this.floatService.findAll());
		result.addObject("requestURI", "float/list.do");
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

		result = this.editFormsUrlId(f, "float/edit.do", map, "/float/list.do", this.custom(new ModelAndView("float/edit")));
		result.addObject("float", f);
		result.addObject("message", message);
		return result;
	}

}
