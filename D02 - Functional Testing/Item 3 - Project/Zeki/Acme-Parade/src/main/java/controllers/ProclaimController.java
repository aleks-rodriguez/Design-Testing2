
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProclaimService;
import domain.Proclaim;

@Controller
@RequestMapping("/proclaim")
public class ProclaimController extends AbstractController {

	@Autowired
	private ProclaimService	serviceProclaim;


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
		if (binding.hasErrors())
			result = this.createEditModelAndView(proclaim);
		else
			try {
				proclaim = this.serviceProclaim.reconstruct(proclaim, binding);
				System.out.println(proclaim);
				this.serviceProclaim.save(proclaim);
				result = new ModelAndView("redirect:../Proclaim/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(proclaim, "user.commit.error");
			}
		return result;
	}
	//Listing the Proclaim of the actor
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProclaim() {
		ModelAndView result;
		result = this.custom(new ModelAndView("Proclaim/list"));
		//result.addObject("proclaims", this.serviceProclaim.getActorByUser(LoginService.getPrincipal().getId()).getProclaims());
		result.addObject("requestURI", "Proclaim/list.do");
		return result;
	}
	//Delete a Proclaim
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteProclaim(@RequestParam final int id) {
		ModelAndView result;

		try {
			this.serviceProclaim.deleteProclaim(id);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = this.createEditModelAndView(this.serviceProclaim.findOne(id), "Proclaim.commit.error");
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

		result = this.editFormsUrlId("proclaim/edit.do", map, "/proclaim/list.do", this.custom(new ModelAndView("proclaim/edit")));
		result.addObject("proclaim", proclaim);
		result.addObject("message", message);
		return result;
	}
}
