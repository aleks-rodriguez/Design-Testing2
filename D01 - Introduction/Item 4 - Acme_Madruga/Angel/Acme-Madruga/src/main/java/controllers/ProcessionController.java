
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;

@Controller
@RequestMapping(value = {
	"/procession/member", "/procession/brotherhood"
})
public class ProcessionController extends AbstractController {

	@Autowired
	private FinderService	serviceFinder;


	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;
		result = this.custom(new ModelAndView("procession/find"));
		result.addObject("finder", this.serviceFinder.memberFinder());
		return result;
	}
}
