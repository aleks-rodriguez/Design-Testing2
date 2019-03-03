/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	serviceAdministrator;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView model;

		model = this.createEditModelAndView(this.serviceAdministrator.createAdministrator());

		return model;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(administrator);
			result.addObject("errors", binding.getAllErrors());
		} else
			try {

				this.serviceAdministrator.save(administrator);
				result = new ModelAndView("redirect:../security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "administrator.commit.error");
				result.addObject("oops", oops.getMessage());
				result.addObject("errors", binding.getAllErrors());
			}

		return result;
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonalData() {
		ModelAndView result;

		//This method is used for personal data edition. Administrators can not change data from other administrators.

		Administrator find;

		find = this.serviceAdministrator.findOne(LoginService.getPrincipal().getId());

		result = this.createEditModelAndView(find);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView model;
		model = this.createEditModelAndView(administrator, null);
		return model;
	}
	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", message);
		return result;

	}

}
