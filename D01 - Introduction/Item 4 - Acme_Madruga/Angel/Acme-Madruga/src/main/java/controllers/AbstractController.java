/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import utilities.Utiles;
import domain.DomainEntity;

@Controller
public class AbstractController {

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = this.custom(new ModelAndView("misc/panic"));
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	public ModelAndView custom(final ModelAndView parameter) {

		parameter.addObject("banner", Utiles.banner);
		parameter.addObject("systemName", Utiles.systemName);
		return parameter;
	}

	public <T extends DomainEntity> ModelAndView editFormsUrlId(final String requestURI, final Map<String, String> requestParams, final String requestCancel, final ModelAndView parameter) {
		String req = requestURI;

		if (requestParams.size() > 0)
			req += "?" + Utiles.buildUrl(requestParams);

		parameter.addObject("requestURI", req);
		parameter.addObject("requestCancel", requestCancel);
		return parameter;
	}
}
