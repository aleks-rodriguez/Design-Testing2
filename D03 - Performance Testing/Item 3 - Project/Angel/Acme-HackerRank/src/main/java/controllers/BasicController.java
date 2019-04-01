
package controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import domain.DomainEntity;

public abstract class BasicController extends AbstractController {

	/*
	 * Estos metodos permiten introducir las operaciones que trabajaran dentro del bloque try.
	 * 
	 * Save
	 * Delete
	 */

	public abstract <T extends DomainEntity> ModelAndView saveAction(T e, BindingResult binding, String nameResolver);
	public abstract <T extends DomainEntity> ModelAndView deleteAction(T e, String nameResolver);

	/*
	 * Lista de Elementos
	 * La diferenciacion entre roles debera hacerse en el controlador hijo.
	 */

	//List
	public <T extends DomainEntity> ModelAndView listModelAndView(final String nameCollection, final String nameView, final Collection<T> objects) {
		ModelAndView result;
		result = this.custom(new ModelAndView(nameView));
		result.addObject(nameCollection, objects);
		return result;
	}
	//Create
	public <T extends DomainEntity> ModelAndView create(final T e, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel) {
		return this.createAndEditModelAndView(e, nameEntity, nameView, requestParams, requestURI, requestCancel);
	}

	//Show
	public <T extends DomainEntity> ModelAndView show(final T e, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel) {
		ModelAndView result;
		result = this.createAndEditModelAndView(e, nameEntity, nameView, requestParams, requestURI, requestCancel);
		result.addObject("view", true);
		return result;
	}
	//Show
	public <T extends DomainEntity> ModelAndView edit(final T e, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel) {
		ModelAndView result;
		result = this.createAndEditModelAndView(e, nameEntity, nameView, requestParams, requestURI, requestCancel);
		result.addObject("view", false);
		return result;
	}

	//Save
	public <T extends DomainEntity> ModelAndView save(final T e, final BindingResult binding, final String error, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel,
		final String nameResolver) {

		ModelAndView result = null;

		try {
			//Llamo al metodo abstracto descrito arriba. En mi clase hija solo tendre que reevaluar el contenido del mismo.
			result = this.custom(this.saveAction(e, binding, nameResolver));
		} catch (final ValidationException valid) {
			result = this.createAndEditModelAndView(e, nameEntity, nameView, requestParams, requestURI, requestCancel);
		} catch (final Throwable oops) {
			result = this.createAndEditModelAndView(e, error, nameEntity, nameView, requestParams, requestURI, requestCancel);
		}

		return this.custom(result);
	}
	// Delete
	public <T extends DomainEntity> ModelAndView delete(final T e, final String error, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel, final String nameResolver) {
		ModelAndView result;
		try {
			result = this.custom(this.deleteAction(e, nameResolver));
		} catch (final Throwable oops) {
			result = this.createAndEditModelAndView(e, error, nameEntity, nameView, requestParams, requestURI, requestCancel);
		}
		return result;
	}

	//Protected Methods
	protected <T extends DomainEntity> ModelAndView createAndEditModelAndView(final T e, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel) {
		ModelAndView result;

		result = this.createAndEditModelAndView(e, null, nameEntity, nameView, requestParams, requestURI, requestCancel);

		return result;
	}
	protected <T extends DomainEntity> ModelAndView createAndEditModelAndView(final T e, final String message, final String nameEntity, final String nameView, final Map<String, String> requestParams, final String requestURI, final String requestCancel) {
		ModelAndView result;

		result = new ModelAndView(nameView);
		result.addObject(nameEntity, e);
		result.addObject("message", message);

		this.editFormsUrlId(requestURI, requestParams, requestCancel, result);

		return this.custom(result);
	}

	// Estos dos metodos permiten construir urls a partir del Map dado
	public void editFormsUrlId(final String requestURI, final Map<String, String> requestParams, final String requestCancel, final ModelAndView parameter) {
		String req = requestURI;

		if (requestParams.size() > 0)
			req += "?" + this.buildUrl(requestParams);

		parameter.addObject("requestURI", req);
		parameter.addObject("requestCancel", requestCancel);
	}

	private String buildUrl(final Map<String, String> requestParams) {
		String s = "";
		if (requestParams.size() > 1)
			for (int i = requestParams.size() - 1; i >= 0; i--) {
				final String key = (String) requestParams.keySet().toArray()[i];
				s = s + key + "=" + requestParams.get(key) + "&";
			}
		else
			s = s + requestParams.keySet().toArray()[0] + "=" + requestParams.values().toArray()[0] + "&";
		return s.substring(0, s.length() - 1);
	}

}
