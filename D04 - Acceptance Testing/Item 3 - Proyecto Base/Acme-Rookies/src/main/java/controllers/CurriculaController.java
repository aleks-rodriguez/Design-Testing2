
package controllers;

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
import services.CurriculaService;
import domain.Curricula;

@Controller
@RequestMapping(value = {
	"/curricula/rookie", "/curricula/company"
})
public class CurriculaController extends BasicController {

	@Autowired
	private CurriculaService	service;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		return super.listModelAndView("curriculas", "curricula/list", this.service.findAllByRookie(), "curricula/rookie/list.do");
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		return super.create(this.service.create(), "curricula/edit", "curricula/rookie/edit.do", "/curricula/rookie/list.do");
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		Curricula c;
		c = this.service.findOne(id);
		Assert.isTrue(this.service.findAllByRookie().contains(c), "You don't have permission to do this");
		return super.edit(c, "curricula/edit", "curricula/rookie/edit.do?id=" + id, "/curricula/rookie/list.do");
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {

		ModelAndView result;

		Curricula c;
		c = this.service.findOne(id);

		String requestCancel;

		if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			requestCancel = "/curricula/rookie/list.do";
		else
			requestCancel = "/";

		result = super.show(c, "curricula/edit", "curricula/rookie/show.do", requestCancel);

		//EducationalData		
		result.addObject("eduDatas", c.getEducationData());
		//MiscellaneousData
		result.addObject("miscDatas", c.getMiscellaneousData());
		//PositionData
		result.addObject("posDatas", c.getPositionsData());

		if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			result.addObject("check", true);
		else
			result.addObject("check", false);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Curricula curricula, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		if (curricula.getId() != 0)
			Assert.isTrue(this.service.findAllByRookie().contains(curricula), "You don't have permission to do this");
		result = super.save(curricula, binding, "curricula.commit.error", "curricula/edit", "curricula/rookie/edit.do", "redirect:list.do", "redirect:list.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curricula curricula) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		if (curricula.getId() != 0)
			Assert.isTrue(this.service.findAllByRookie().contains(curricula), "You don't have permission to do this");
		return super.delete(curricula, "curricula.commit.error", "curricula/edit", "curricula/rookie/edit.do", "/curricula/rookie/list.do", "redirect:list.do");
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		Curricula c;
		c = (Curricula) e;
		c = this.service.reconstruct(c, binding);
		this.service.save(c);

		ModelAndView result;
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		Curricula c;
		c = (Curricula) e;
		this.service.delete(c.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
