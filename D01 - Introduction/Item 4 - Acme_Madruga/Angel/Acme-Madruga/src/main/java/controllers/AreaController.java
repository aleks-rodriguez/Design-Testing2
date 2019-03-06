
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.AreaService;
import utilities.Utiles;
import domain.Area;

@Controller
@RequestMapping(value = {
	"/area/brotherhood", "/area/administrator"
})
public class AreaController extends AbstractController {

	@Autowired
	private AreaService	areaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.custom(new ModelAndView("area/list"));
		Collection<Area> areas;
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
			areas = this.areaService.findAll();
			result.addObject("areas", areas);
			result.addObject("requestURI", "area/administrator/list.do");
		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD)) {
			areas = new ArrayList<Area>();
			Area a;
			a = this.areaService.findByUserAccount().getArea();
			if (a == null)
				areas = this.areaService.findAll();
			else
				areas.add(a);

			result.addObject("areas", areas);
			result.addObject("requestURI", "area/brotherhood/list.do");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = this.custom(this.createEditModelAndView(this.areaService.create()));
		result.addObject("area", this.areaService.create());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;
		Area area;
		area = this.areaService.findOne(id);
		result = this.custom(this.createEditModelAndView(area));
		result.addObject("area", area);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Area area, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				result = this.custom(new ModelAndView("redirect:list.do"));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final int id) {
		ModelAndView result;
		Area area;
		area = this.areaService.findOne(id);
		try {
			this.areaService.delete(area);
			result = this.custom(new ModelAndView("redirect:list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(area, "area.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int area) {
		ModelAndView result = null;
		//Expected true if brotherhood´s area is null
		final boolean nonArea = this.areaService.setAreaToBrotherhood(area);

		if (nonArea)
			result = this.custom(new ModelAndView("redirect:list.do"));

		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area) {
		return this.createEditModelAndView(area, null);
	}

	protected ModelAndView createEditModelAndView(final Area area, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("area/edit"));
		result.addObject("message", message);
		result.addObject("requestURI", "area/administrator/edit.do");
		result.addObject("requestCancel", "area/administrator/list.do");
		return result;
	}
}
