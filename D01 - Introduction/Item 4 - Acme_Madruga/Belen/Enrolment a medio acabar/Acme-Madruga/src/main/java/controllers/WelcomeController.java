/*
 * WelcomeController.java
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationSystemService;
import utilities.Utiles;
import domain.CustomisationSystem;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private CustomisationSystemService	serviceCustom;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;

		final CustomisationSystem custom = this.serviceCustom.findUnique();

		Utiles.setParameters(custom.getSystemName(), custom.getBanner(), custom.getMessage(), custom.getHoursFinder(), custom.getResultFinder(), custom.getPhonePrefix());

		result = this.custom(new ModelAndView("welcome/index"));

		result.addObject("mess", Utiles.mess);

		Utiles.goodWords.addAll(custom.getGoodWords());
		Utiles.badWords.addAll(custom.getBadWords());
		Utiles.spamWords.addAll(custom.getSpamWords());
		Utiles.priorities.addAll(custom.getPriorities());

		return result;
	}
}
