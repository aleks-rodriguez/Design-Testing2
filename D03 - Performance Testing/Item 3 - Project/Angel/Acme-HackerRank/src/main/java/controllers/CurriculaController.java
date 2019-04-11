
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
import domain.DomainEntity;

@Controller
@RequestMapping(value = {
	"/curricula/hacker", "/curricula/company"
})
public class CurriculaController extends BasicController {

	@Autowired
	private CurriculaService	service;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER));
		return super.listModelAndView("curriculas", "curricula/list", this.service.findAllByHacker(), "curricula/hacker/list.do");
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER));
		return super.create(this.service.create(), "curricula", "curricula/edit", "curricula/hacker/edit.do", "/curricula/hacker/list.do");
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER));
		Curricula c;
		c = this.service.findOne(id);
		Assert.isTrue(this.service.findAllByHacker().contains(c));
		return super.edit(c, "curricula", "curricula/edit", "curricula/hacker/edit.do?id=" + id, "/curricula/hacker/list.do");
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {

		ModelAndView result;

		Curricula c;
		c = this.service.findOne(id);

		String requestCancel;

		if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER))
			requestCancel = "/curricula/hacker/list.do";
		else
			requestCancel = "/";

		result = super.show(c, "curricula", "curricula/edit", "curricula/hacker/show.do", requestCancel);

		//EducationalData		
		result.addObject("eduDatas", c.getEducationData());
		//MiscellaneousData
		result.addObject("miscDatas", c.getMiscellaneousData());
		//PositionData
		result.addObject("posDatas", c.getPositionsData());

		if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER))
			result.addObject("check", true);
		else
			result.addObject("check", false);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Curricula curricula, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER));
		if (curricula.getId() != 0)
			Assert.isTrue(this.service.findAllByHacker().contains(curricula));
		result = super.save(curricula, binding, "curricula.commit.error", "curricula", "curricula/edit", "curricula/hacker/edit.do", "redirect:list.do", "redirect:list.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curricula curricula) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER));
		if (curricula.getId() != 0)
			Assert.isTrue(this.service.findAllByHacker().contains(curricula));
		return super.delete(curricula, "curricula.commit.error", "curricula", "curricula/edit", "curricula/hacker/edit.do", "/curricula/hacker/list.do", "redirect:list.do");
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
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		this.service.delete(e.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
