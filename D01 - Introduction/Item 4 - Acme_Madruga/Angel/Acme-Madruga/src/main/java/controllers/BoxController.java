
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BoxService;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	@Autowired
	private BoxService	boxService;


	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.custom(new ModelAndView("box/list"));
		result.addObject("boxes", this.boxService.getBoxesFromUserAccount(LoginService.getPrincipal().getId()));
		result.addObject("requestURI", "box/list.do");
		return result;
	}

	//create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int parent) {
		ModelAndView result;
		final Box b;
		b = this.boxService.createBox();
		result = this.createEditModelAndView(b);
		result.addObject("parent", parent);
		return result;
	}
	//save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int parent, Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {
				box = this.boxService.reconstruct(box, binding);
				if (parent != 0)
					this.boxService.saveSubBox(box, this.boxService.findOne(parent));
				else
					this.boxService.save(box);
				result = new ModelAndView("redirect:../box/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(box, "box.commit.error");
				result.addObject("parent", parent);
			}
		return result;
	}
	//update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateBox(@RequestParam final int id) {
		ModelAndView result;
		Box b;
		b = this.boxService.findOne(id);
		if (!this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()).getBoxes().contains(b) || b.isFromSystem())
			result = new ModelAndView("redirect:../");
		else
			result = this.createEditModelAndView(b);
		result.addObject("box", b);
		return result;
	}
	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result = null;
		final Box b;
		b = this.boxService.findOne(id);
		try {
			if (!b.isFromSystem()) {
				this.boxService.delete(b);
				result = this.custom(new ModelAndView("redirect:list.do"));
			}
		} catch (final Throwable opps) {
			result = this.createEditModelAndView(this.boxService.findOne(id), "box.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView result;
		result = this.createEditModelAndView(box, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("box/edit"));
		result.addObject("box", box);
		result.addObject("message", message);
		return result;
	}

}
