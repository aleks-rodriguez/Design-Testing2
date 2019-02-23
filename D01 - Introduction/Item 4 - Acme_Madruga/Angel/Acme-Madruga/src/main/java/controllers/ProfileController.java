/*
 * ProfileController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ProfileService;
import domain.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ProfileService	serviceProfile;


	public ProfileController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createEditModelAndView(this.serviceProfile.createProfile());

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;

		result = this.createEditModelAndView(this.serviceProfile.findOne(id));

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(Profile profile, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(profile);
		else
			try {
				profile = this.serviceProfile.reconstruct(profile, binding);
				System.out.println(profile);
				this.serviceProfile.save(profile);
				result = new ModelAndView("redirect:../profile/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(profile, "user.commit.error");
			}
		return result;
	}
	//Listing the profile of the actor
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProfile() {
		ModelAndView result;
		result = this.custom(new ModelAndView("profile/list"));
		result.addObject("profiles", this.serviceProfile.getActorByUser(LoginService.getPrincipal().getId()).getProfiles());
		result.addObject("requestURI", "profile/list.do");
		return result;
	}
	//Delete a profile
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteProfile(@RequestParam final int id) {
		ModelAndView result;

		try {
			this.serviceProfile.deleteProfile(id);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = this.createEditModelAndView(this.serviceProfile.findOne(id), "profile.commit.error");
		}
		return result;
	}
	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Profile profile) {
		ModelAndView model;
		model = this.createEditModelAndView(profile, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Profile profile, final String message) {
		ModelAndView result;
		result = this.editFormsUrlId(profile, "profile/edit.do", "id", this.custom(new ModelAndView("profile/edit")));
		result.addObject("profile", profile);
		result.addObject("message", message);
		return result;
	}

}
