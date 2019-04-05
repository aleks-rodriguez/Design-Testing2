
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
import services.PositionService;
import domain.Company;
import domain.DomainEntity;
import domain.Position;

@Controller
@RequestMapping(value = {
	"/position/company", "/position"
})
public class PositionController extends BasicController {

	@Autowired
	private PositionService	service;


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
			}
		} catch (final IllegalArgumentException e) {
			if (company != 0) {
				col = this.service.getPublicPositionsByCompany(company);
				requestURI = "position/list.do";
			} else {
				col = this.service.findAll();
				requestURI = "position/list.do";
			}

		}

		return super.listModelAndView("positions", "position/list", col, requestURI);
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
		return super.create(this.service.create(), "position", "position/edit", new HashMap<String, String>(), "position/edit.do", "/position/company/list.do");
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
					result = super.edit(p, "position", "position/edit", new HashMap<String, String>(), "position/company/edit.do", "/position/company/list.do");
				else
					throw new IllegalArgumentException();
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			/**
			 * Same kind of actor but it is not his position
			 */
			result = super.show(p, "position", "position/edit", new HashMap<String, String>(), "position/edit.do", "/position/list.do?company=" + p.getCompany().getId());
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saved(final Position position, final BindingResult binding) {
		ModelAndView result;
		result = super.save(position, binding, "position.commit.error", "position", "position/edit", new HashMap<String, String>(), "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position) {
		ModelAndView result;
		result = super.delete(position, "position.commit.error", "position", "position/edit", new HashMap<String, String>(), "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
		return result;
	}
	@Override
	public <T extends DomainEntity> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Position p;
		p = (Position) e;
		p = this.service.reconstruct(p, binding);
		this.service.save(p);
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
