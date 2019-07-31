
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
import services.ActorService;
import services.ApplicationService;
import services.CurriculaService;
import services.MessageService;
import services.PositionService;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Rookie;

@Controller
@RequestMapping(value = {
	"/application/company", "/application/rookie"
})
public class ApplicationController extends BasicController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int position) {
		ModelAndView result = null;
		UserAccount user;
		user = LoginService.getPrincipal();

		Actor a;

		try {
			a = this.applicationService.findActorByUserAccountId(user.getId());

			if (this.applicationService.findAuthority(user.getAuthorities(), Authority.ROOKIE)) {
				Rookie h;
				h = (Rookie) a;
				result = super.listModelAndView("applications", "application/list", this.applicationService.getApplicationByRookieId(h.getId()), "application/rookie/list.do");
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
		Rookie h;
		h = (Rookie) this.applicationService.findActorByUserAccountId(user.getId());
		Assert.isTrue(this.applicationService.getApplicationByPositionIdAndRookie(h.getId(), idPosition).size() < 1, "You don't have permission to do this because you've already created an application before");
		result = super.create(this.applicationService.createApplication(idPosition), "application/edit", "application/rookie/edit.do", "application/rookie/list.do");
		if (this.applicationService.findAuthority(user.getAuthorities(), Authority.ROOKIE))
			result.addObject("curriculas", this.curriculaService.findAllByRookie());
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
		if (this.applicationService.findAuthority(user.getAuthorities(), Authority.ROOKIE)) {
			Rookie h;
			h = (Rookie) this.applicationService.findActorByUserAccountId(user.getId());
			col = this.applicationService.getApplicationByRookieId(h.getId());
			Assert.isTrue(col.contains(a), "You don't have permission to do this");
			requestURI = "application/rookie/edit.do";
			requestCancel = "/application/rookie/list.do";
		} else if (this.applicationService.findAuthority(user.getAuthorities(), Authority.COMPANY)) {
			Company c;
			c = (Company) this.applicationService.findActorByUserAccountId(user.getId());
			final Collection<Position> colPos = this.positionService.getPositionsByCompany(c.getId());
			Assert.isTrue(colPos.contains(a.getPosition()), "You don't have permission to do this");
			requestURI = "application/company/edit.do";
			requestCancel = "/application/company/list.do?position=" + a.getPosition().getId();
		}

		ModelAndView result;

		result = super.edit(this.applicationService.findOne(idApplication), "application/edit", requestURI, requestCancel);

		result.addObject("statusCol", Arrays.asList("ACCEPTED", "REJECTED"));
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
			requestURI = "application/rookie/edit.do";
			requestCancel = "/application/rookie/list.do";
		}

		result = super.show(a, "application/edit", requestURI, requestCancel);
		result.addObject("idCurr", this.applicationService.findOne(idApplication).getCurricula().getId());
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
			requestURI = "application/rookie/edit.do";
			requestCancel = "/application/rookie/list.do";
			nameResolver = "redirect:list.do";
		}

		result = super.save(application, binding, "application.commit.error", "application/edit", requestURI, requestCancel, nameResolver);
		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			result.addObject("statusCol", Arrays.asList("ACCEPTED", "REJECTED"));
		else
			result.addObject("statusCol", Arrays.asList("PENDING", "SUBMITTED", "ACCEPTED", "REJECTED"));
		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			result.addObject("curriculas", this.curriculaService.findAllByRookie());
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Application application;
		application = (Application) e;
		application = this.applicationService.reconstruct(application, binding);
		Application saved;
		saved = this.applicationService.save(application);
		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			this.messageService.createMessageNotifyChangeStatusAply(saved.getRookie(), saved.getStatus());
		result = new ModelAndView(nameResolver);
		if (this.applicationService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			result.addObject("curriculas", this.curriculaService.findAllByRookie());
		return result;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		return null;
	}

}
