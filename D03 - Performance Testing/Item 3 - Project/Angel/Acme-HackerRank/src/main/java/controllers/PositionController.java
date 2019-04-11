
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.FinderService;
import services.PositionService;
import domain.Company;
import domain.DomainEntity;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping(value = {
	"/position/company", "/position"
})
public class PositionController extends BasicController {

	@Autowired
	private PositionService	service;

	@Autowired
	private FinderService	serviceFinder;

	private boolean			duplicate;
	private boolean			control	= true;


	/**
	 * List de Companies
	 */

	@RequestMapping(value = "/listCompanies", method = RequestMethod.GET)
	public ModelAndView listCompanies() {
		return super.listModelAndView("companies", "company/list", this.service.findAllCompanies(), "position/listCompanies.do");
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int company) {

		Collection<Position> col;
		col = new ArrayList<Position>();
		String requestURI = "";
		try {
			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
				final Company c = (Company) this.service.findByUserAccount(LoginService.getPrincipal().getId());
				col = this.service.getPositionsByCompany(c.getId());
				requestURI = "position/company/list.do";
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			if (company != 0) {
				col = this.service.getPublicPositionsByCompany(company);
				requestURI = "position/list.do";
			} else {
				col = this.service.getPublicPositions();
				requestURI = "position/list.do";
			}

		}

		return super.listModelAndView("positions", "position/list", col, requestURI);
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
		return super.create(this.service.create(), "position", "position/edit", "position/edit.do", "/position/company/list.do");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result = null;
		Position p;
		p = this.service.findOne(id);
		try {
			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
				Company c;
				c = (Company) this.service.findByUserAccount(LoginService.getPrincipal().getId());
				if (p.getCompany().equals(c))
					/**
					 * When position search owns to logged user
					 */
					result = super.edit(p, "position", "position/edit", "position/company/edit.do", "/position/company/list.do");
				else
					throw new IllegalArgumentException();
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			/**
			 * Same kind of actor but it is not his position
			 */
			if (p.isFinalMode())
				result = super.show(p, "position", "position/edit", "position/edit.do", "/position/list.do?company=" + p.getCompany().getId());
			else
				result = this.custom(new ModelAndView("redirect:/misc/403.jsp"));

		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saved(final Position position, final BindingResult binding) {
		ModelAndView result = null;

		boolean res = false;

		if (position.isFinalMode()) {
			if (position.getId() == 0)
				res = false;
			else
				res = this.service.problemsByPosition(position.getId()).size() >= 2;
		} else
			res = false;

		position.setFinalMode(res ? position.isFinalMode() : false);

		if (position.getDeadline().after(new Date()))
			do {
				result = super.save(position, binding, "position.commit.error", "position", "position/edit", "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
				if (this.control)
					this.duplicate = (boolean) result.getModel().get("duplicate");
				else
					this.duplicate = false;
			} while (this.duplicate == true);
		else
			result = super.createAndEditModelAndView(position, "deadline.future", "position", "position/edit", "position/company/edit.do", "/position/company/list.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position) {
		ModelAndView result;
		result = super.delete(position, "position.commit.error", "position", "position/edit", "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "cancelPos")
	public ModelAndView cancel(final Position position) {
		ModelAndView result;
		this.service.cancel(position);
		result = this.custom(new ModelAndView("redirect:list.do"));
		return result;
	}

	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;

		Finder finder;

		Hacker h;

		try {
			h = (Hacker) this.service.findByUserAccount(LoginService.getPrincipal().getId());
			finder = h.getFinder();
		} catch (final IllegalArgumentException e) {
			finder = this.serviceFinder.create();
		}

		result = super.create(finder, "finder", "position/find", "position/search.do", "/");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(Finder finder, final BindingResult binding) {

		ModelAndView result = null;

		Collection<Position> res;
		res = new ArrayList<Position>();

		try {

			finder = this.serviceFinder.reconstruct(finder, binding);

			if (finder.getId() == 0)
				res = this.serviceFinder.findBySingleKey(finder.getSingleKey());
			else
				//Modificar aqui por aleks. Falta montar el save y la operativa para los distintos parametros
				res = null;

		} catch (final ValidationException e) {
			result = super.createAndEditModelAndView(finder, "finder", "position/find", "position/search.do", "/");
		} catch (final Throwable oops) {
			result = super.createAndEditModelAndView(finder, "finder.commit.error", "finder", "position/find", "position/search.do", "/");
		}

		result = super.listModelAndView("positions", "position/list", res, "position/list.do");

		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Position p;
		p = (Position) e;
		p = this.service.reconstruct(p, binding);
		this.service.save(p, this.duplicate);
		this.control = false;
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		this.service.delete(((Position) e).getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
