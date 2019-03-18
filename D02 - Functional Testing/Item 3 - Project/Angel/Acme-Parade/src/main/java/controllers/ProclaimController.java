
package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ProclaimService;
import domain.Proclaim;

@Controller
@RequestMapping(value = {
	"/proclaim", "/proclaim/chapter"
})
public class ProclaimController extends AbstractController {

	@Autowired
	private ProclaimService	serviceProclaim;


	//Listing the Proclaim of the actor
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProclaim(@RequestParam(defaultValue = "0") final int chapter) {
		ModelAndView result;
		result = this.custom(new ModelAndView("proclaim/list"));
		if (chapter == 0) {
			result.addObject("proclaims", this.serviceProclaim.findProclaimsByChapter(this.serviceProclaim.findByUA(LoginService.getPrincipal().getId()).getId()));
			result.addObject("requestURI", "proclaim/chapter/list.do");
		} else {
			result.addObject("proclaims", this.serviceProclaim.findProclaimsByChapterFinalMode(chapter));
			result.addObject("requestURI", "proclaim/list.do?chapter=" + chapter);
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createEditModelAndView(this.serviceProclaim.createProclaim());

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;

		result = this.createEditModelAndView(this.serviceProclaim.findOne(id));

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(Proclaim proclaim, final BindingResult binding) {
		ModelAndView result;

		try {
			proclaim = this.serviceProclaim.reconstruct(proclaim, binding);
			System.out.println(proclaim);
			this.serviceProclaim.save(proclaim);
			result = new ModelAndView("redirect:../chapter/list.do");
		} catch (final ValidationException e) {
			proclaim.setFinalMode(false);
			result = this.createEditModelAndView(proclaim);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(proclaim, "proclaim.commit.error");
		}
		return result;
	}

	//Delete a Proclaim
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteProclaim(final Proclaim proclaim) {
		ModelAndView result;
		final int id = proclaim.getId();
		try {
			this.serviceProclaim.deleteProclaim(id);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = this.createEditModelAndView(this.serviceProclaim.findOne(id), "proclaim.commit.error");
		}
		return result;
	}
	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Proclaim proclaim) {
		ModelAndView model;
		model = this.createEditModelAndView(proclaim, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(proclaim.getId()));

		result = this.editFormsUrlId("proclaim/chapter/edit.do", map, "/proclaim/chapter/list.do", this.custom(new ModelAndView("proclaim/edit")));
		result.addObject("proclaim", proclaim);
		result.addObject("message", message);
		return result;
	}
}
