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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import domain.CustomisationSystem;

@Controller
public class AbstractController {

	//Realizo una copia al cargar desde WelcomeController

	public void setCustomisation(final CustomisationSystem param) {

		System.setProperty("systemName", param.getSystemName());
		System.setProperty("mess", param.getMessage());
		System.setProperty("banner", param.getBanner());
		System.setProperty("phonePrefix", param.getBanner());
		System.setProperty("spamwords", param.getSpamwords().get(this.getLanguageSystem()));
	}

	// Panic handler ----------------------------------------------------------
	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return this.custom(result);
	}

	public boolean checkPhone(String phone) {
		boolean res;
		res = false;

		phone = phone.replace(("+" + System.getProperty("phonePrefix")).trim(), " ").trim();
		if (phone.contains("(") && phone.contains(")")) {
			phone = phone.replace("(", "");
			phone = phone.replace(")", "");
			phone = phone.replace(" ", "");

		}

		Long phoneStringToInteger;

		try {
			phoneStringToInteger = Long.valueOf(phone);
			res = true;
		} catch (final NumberFormatException e) {
			res = false;
		}
		return res;
	}

	public boolean luhnAlgorithm(final String cadena) {
		int[] str;
		str = new int[cadena.length()];

		for (int i = 0; i < str.length; i++)
			str[i] = Integer.parseInt(cadena.substring(i, i + 1));

		for (int i = str.length - 2; i >= 0; i = i - 2) {

			int j = str[i];
			j = j * 2;

			if (j > 9)
				j = j % 10 + 1;

			str[i] = j;

		}
		int sum = 0;

		for (int i = 0; i < str.length; i++)
			sum = sum + str[i];

		return sum % 10 == 0;
	}
	public String getLanguageSystem() {
		return LocaleContextHolder.getLocale().getLanguage();

	}
	public Set<String> statusByLang() {
		Map<String, Set<String>> result;
		result = new HashMap<String, Set<String>>();

		result.put("en", new HashSet<>(Arrays.asList("SUBMITTED", "ACCEPTED", "REJECTED")));
		result.put("es", new HashSet<>(Arrays.asList("ENVIADO", "ACEPTADO", "RECHAZADO")));

		return result.get(this.getLanguageSystem());
	}

	public boolean checkURL(final Collection<String> urls, final boolean optional) {
		boolean res = false;

		for (final String url : urls) {
			res = url.startsWith("http://") || url.startsWith("https://");
			if (res == false)
				break;
		}

		return urls.size() > 0 ? res : optional;
	}
	//CustomisationSystem for Controllers ModelAndView

	public ModelAndView custom(final ModelAndView parameter) {
		parameter.addObject("systemName", System.getProperty("systemName"));
		parameter.addObject("mess", System.getProperty("mess"));
		parameter.addObject("banner", System.getProperty("banner"));
		return parameter;
	}

}
