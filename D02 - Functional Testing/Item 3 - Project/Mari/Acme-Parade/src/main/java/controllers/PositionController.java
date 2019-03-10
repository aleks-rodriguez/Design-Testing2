
package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping(value = "/position/administrator")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	servicePosition;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.custom(new ModelAndView("position/list"));
		result.addObject("positions", this.servicePosition.findAll());
		result.addObject("requestURI", "position/administrator/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = this.createEditModelAndView(this.servicePosition.create());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;
		result = this.createEditModelAndView(this.servicePosition.findOne(id));
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.servicePosition.save(position);
				result = this.custom(new ModelAndView("redirect:list.do"));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position) {
		ModelAndView result;
		boolean res = false;
		try {
			res = this.servicePosition.delete(position.getId());
			if (res == false)
				result = new ModelAndView("redirect:list.do");
			else
				result = this.createEditModelAndView(position, "position.delete.message");
		} catch (final Throwable opps) {
			result = this.createEditModelAndView(position, "position.commit.error");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Position position) {
		return this.createEditModelAndView(position, null);
	}

	protected ModelAndView createEditModelAndView(final Position position, final String message) {
		ModelAndView result;
		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(position.getId()));
		result = this.editFormsUrlId("position/administrator/edit.do", map, "/position/administrator/list.do", this.custom(new ModelAndView("position/edit")));
		result.addObject("position", position);
		result.addObject("message", message);
		return result;
	}

}
