
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping(value = {
	"/procession/member", "/procession/brotherhood", "/procession"
})
public class ProcessionController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private FinderService		serviceFinder;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProcession() {
		ModelAndView result;
		result = new ModelAndView("procession/list");
		result.addObject("processions", this.processionService.findAll());
		result.addObject("requestURI", "procession/list.do");
		return result;
	}

	// Finder
	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;
		result = this.custom(new ModelAndView("procession/find"));
		result.addObject("finder", this.serviceFinder.memberFinder());
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView model;
		model = this.createEditModelAndView(procession, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(procession.getId()));

		result = this.editFormsUrlId(procession, "procession/edit.do", map, "/procession/list.do", this.custom(new ModelAndView("procession/edit")));
		result.addObject("procession", procession);
		result.addObject("message", message);
		return result;
	}
}
