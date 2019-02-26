
package controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import domain.Finder;

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
		final Finder finder = this.serviceFinder.memberFinder();
		result = this.editFormsUrlId("finder", finder, "", new HashMap<String, String>(), "/welcome/index.do", this.custom(new ModelAndView("procession/find")));

		return result;
	}
}
