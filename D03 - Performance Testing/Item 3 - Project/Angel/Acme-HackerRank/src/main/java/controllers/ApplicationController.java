
package controllers;

import java.util.Arrays;
import java.util.Collection;

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
import security.UserAccount;
import services.ApplicationService;
import services.CurriculaService;
import services.PositionService;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.DomainEntity;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping(value = {
	"/application/company", "/application/hacker"
})
public class ApplicationController extends BasicController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculaService	curriculaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int position) {
		ModelAndView result = null;
		UserAccount user;
		user = LoginService.getPrincipal();

		Actor a;

		try {
			a = this.applicationService.findActorByUserAccountId(user.getId());

			if (this.applicationService.findAuthority(user.getAuthorities(), Authority.HACKER)) {
				Hacker h;
				h = (Hacker) a;
				result = super.listModelAndView("applications", "application/list", this.applicationService.getApplicationByHackerId(h.getId()), "application/hacker/list.do");
			} else if (this.applicationService.findAuthority(user.getAuthorities(), Authority.COMPANY)) {
				Company c;
				c = (Company) a;
				Assert.isTrue(this.positionService.getPositionsByCompany(c.getId()).contains(this.positionService.findOne(position)));
				result = super.listModelAndView("applications", "application/list", this.applicationService.findApplicationsByPositionId(position), "application/company/list.do");
			}

		} catch (final IllegalArgumentException e) {
			result = this.custom(new ModelAndView("misc/panic"));
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idPosition) {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Hacker h;
		h = (Hacker) this.applicationService.findActorByUserAccountId(user.getId());
		System.out.println(this.applicationService.getApplicationByPositionIdAndHacker(h.getId(), idPosition).size());
		Assert.isTrue(this.applicationService.getApplicationByPositionIdAndHacker(h.getId(), idPosition).size() < 1, "You don't have permission to do this because you've already created an application before");
		result = super.create(this.applicationService.createApplication(idPosition), "application", "application/edit", "application/hacker/edit.do", "application/hacker/list.do");
		if (this.applicationService.findAuthority(user.getAuthorities(), Authority.HACKER))
			result.addObject("curriculas", this.curriculaService.findAllByHacker());
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idApplication) {
		UserAccount user;
		user = LoginService.getPrincipal();

		Application a;
		a = this.applicationService.findOne(idApplication);

		Collection<Application> col;
		String requestURI = "";
		String requestCancel = "";
		if (this.applicationService.findAuthority(user.getAuthorities(), Authority.HACKER)) {
			Hacker h;
			h = (Hacker) this.applicationService.findActorByUserAccountId(user.getId());
			col = this.applicationService.getApplicationByHackerId(h.getId());
			Assert.isTrue(col.contains(a), "You don't have permission to do this");
			requestURI = "application/hacker/edit.do";
			requestCancel = "/application/hacker/list.do";
		} else if (this.applicationService.findAuthority(user.getAuthorities(), Authority.COMPANY)) {
			Company c;
			c = (Company) this.applicationService.findActorByUserAccountId(user.getId());
			final Collection<Position> colPos = this.positionService.getPositionsByCompany(c.getId());
			Assert.isTrue(colPos.contains(a.getPosition()), "You don't have permission to do this");
			requestURI = "application/company/edit.do";
			requestCancel = "/application/company/list.do?position=" + a.getPosition().getId();
		}

		ModelAndView result;
		result = super.edit(this.applicationService.findOne(idApplication), "application", "application/edit", requestURI, requestCancel);

		result.addObject("statusCol", Arrays.asList("PENDING", "SUBMITTED", "ACCEPTED", "REJECTED"));
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idApplication) {
		ModelAndView result;

		String requestURI;
		String requestCancel;
		final Application a = this.applicationService.findOne(idApplication);

		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
			requestURI = "application/company/edit.do";
			requestCancel = "/application/company/list.do?position=" + a.getPosition().getId();
		} else {
			requestURI = "application/hacker/edit.do";
			requestCancel = "/application/hacker/list.do";
		}

		result = super.show(a, "application", "application/edit", requestURI, requestCancel);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEntity(final Application application, final BindingResult binding) {
		ModelAndView result = null;

		String requestURI;
		String requestCancel;
		String nameResolver;

		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
			requestURI = "application/company/edit.do";
			requestCancel = "/application/company/list.do?position=" + application.getPosition().getId();
			nameResolver = "redirect:list.do?position=" + application.getPosition().getId();
		} else {
			requestURI = "application/hacker/edit.do";
			requestCancel = "/application/hacker/list.do";
			nameResolver = "redirect:list.do";
		}

		result = super.save(application, binding, "application.commit.error", "application", "application/edit", requestURI, requestCancel, nameResolver);
		result.addObject("statusCol", Arrays.asList("PENDING", "SUBMITTED", "ACCEPTED", "REJECTED"));
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Application application;
		application = (Application) e;
		application = this.applicationService.reconstruct(application, binding);
		this.applicationService.save(application);
		result = new ModelAndView(nameResolver);
		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER))
			result.addObject("curriculas", this.curriculaService.findAllByHacker());
		return result;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		return null;
	}

}
