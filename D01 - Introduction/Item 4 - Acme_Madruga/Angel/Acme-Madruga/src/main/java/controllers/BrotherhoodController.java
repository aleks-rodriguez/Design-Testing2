
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import domain.Brotherhood;

@Controller
@RequestMapping(value = {
	"/brotherhood"
})
public class BrotherhoodController {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model;

		model = this.createEditModelAndView(this.brotherhoodService.createBrotherhoodd());

		return model;
	}

	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@Valid final Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView model;

		if (binding.hasErrors()) {
			model = this.createEditModelAndView(brotherhood);
			model.addObject("errors", binding.getAllErrors());
		} else
			try {
				this.brotherhoodService.save(brotherhood);
				model = new ModelAndView("redirect:../security/login.do");
			} catch (final Throwable oops) {
				model = this.createEditModelAndView(brotherhood, "brotherhood.commit.error");
				model.addObject("oops", oops.getMessage());
				model.addObject("errors", binding.getAllErrors());
			}
		return model;
	}

	//Personal data ---------------------------------------------------------------	

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonalData() {
		ModelAndView result;

		Brotherhood find;

		find = (Brotherhood) this.brotherhoodService.findByUserAccount(LoginService.getPrincipal().getId());

		result = this.createEditModelAndView(find);
		result.addObject("view", false);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView model;
		model = this.createEditModelAndView(brotherhood, null);
		return model;
	}
	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String message) {
		ModelAndView model;
		model = new ModelAndView("brotherhood/edit");
		model.addObject("brotherhood", brotherhood);
		model.addObject("message", message);
		return model;
	}

}
