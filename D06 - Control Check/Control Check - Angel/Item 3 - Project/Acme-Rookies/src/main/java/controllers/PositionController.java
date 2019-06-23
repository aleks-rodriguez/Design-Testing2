
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
import services.ActorService;
import services.AuditService;
import services.FinderService;
import services.MessageService;
import services.PositionService;
import services.SponsorshipService;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.Rookie;
import domain.Sponsorship;

@Controller
@RequestMapping(value = {
	"/position/company", "/position"
})
public class PositionController extends BasicController {

	@Autowired
	private PositionService		service;

	@Autowired
	private FinderService		serviceFinder;

	@Autowired
	private ActorService		serviceActor;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private MessageService		serviceMess;

	@Autowired
	private AuditService		auditService;

	private Finder				oldRookieFinder;


	@RequestMapping(value = "/showCompany", method = RequestMethod.GET)
	public ModelAndView showCompanyByPosition(@RequestParam final int position) {
		Company c;
		ModelAndView result;
		c = this.service.showCompanyByPosition(position);
		result = super.show(this.serviceActor.map(this.service.showCompanyByPosition(position), Authority.COMPANY), "actor/edit", "actor/edit.do", "/position/list.do");
		if (super.getLanguageSystem().equals("en") || super.getLanguageSystem() == null) {
			if (c.isSpammer())
				result.addObject("spammer", "Yes");
			else
				result.addObject("spammer", "No");
		} else if (c.isSpammer())
			result.addObject("spammer", "Si");
		else
			result.addObject("spammer", "No");
		result.addObject("score", super.homothetic(this.auditService.findAllScoresByCompanyId(c.getId())));
		result.addObject("view", true);
		result.addObject("authority", Authority.COMPANY);
		return result;
	}
	/**
	 * List de Companies
	 */

	@RequestMapping(value = "/listCompanies", method = RequestMethod.GET)
	public ModelAndView listCompanies() {
		return super.listModelAndView("companies", "company/list", this.service.findAllCompanies(), "position/listCompanies.do");
	}

	@RequestMapping(value = "listPositions", method = RequestMethod.GET)
	public ModelAndView listPositions() {
		ModelAndView result;
		result = super.listModelAndView("positions", "position/list", this.service.getPublicPositions(), "position/listPositions.do");
		result.addObject("publica", false);
		try {
			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
				Collection<Position> colP;
				colP = this.service.findPositionWithSponsorshipByProviderId(this.serviceActor.findByUserAccount(LoginService.getPrincipal().getId()).getId());
				result.addObject("spo", colP);
			} else if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
				Collection<Position> colP2;
				colP2 = this.service.findPositionWithApplyByRookieId(this.serviceActor.findByUserAccount(LoginService.getPrincipal().getId()).getId());
				result.addObject("appl", colP2);
			}
		} catch (final Throwable oops) {

		}
		return result;

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
			if (company != 0)
				col = this.service.getPublicPositionsByCompany(company);
			else
				col = this.service.getPublicPositions();
			requestURI = "position/listPositions.do";
		}
		ModelAndView result;
		result = super.listModelAndView("positions", "position/list", col, requestURI);
		result.addObject("publica", true);
		try {
			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
				Collection<Position> colP;
				colP = this.service.findPositionWithSponsorshipByProviderId(this.serviceActor.findByUserAccount(LoginService.getPrincipal().getId()).getId());
				result.addObject("spo", colP);
			} else if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
				Collection<Position> colP2;
				colP2 = this.service.findPositionWithApplyByRookieId(this.serviceActor.findByUserAccount(LoginService.getPrincipal().getId()).getId());
				result.addObject("appl", colP2);
			}
		} catch (final Throwable oops) {

		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
		return super.create(this.service.create(), "position/edit", "position/edit.do", "/position/company/list.do");
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
				if (p.getCompany().equals(c)) {
					/**
					 * When position search owns to logged user
					 */
					result = super.edit(p, "position/edit", "position/company/edit.do", "/position/company/list.do");
					Collection<Sponsorship> col;
					if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
						col = this.sponsorshipService.getSponsorshipByPositionId(p.getId());
						Sponsorship sponsorship;
						if (col.size() > 0) {
							sponsorship = super.randomizeSponsorships(col);
							result.addObject("linkBanner", sponsorship.getBanner());
							sponsorship.setFlat_rate(sponsorship.getFlat_rate() + this.sponsorshipService.getFlatRate());
						}
					}
				} else
					throw new IllegalArgumentException();
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			/**
			 * Same kind of actor but it is not his position
			 */
			if (p.isFinalMode()) {
				result = super.show(p, "position/edit", "position/edit.do", "/position/list.do?company=" + p.getCompany().getId());
				Collection<Sponsorship> col;
				//			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
				col = this.sponsorshipService.getSponsorshipByPositionId(p.getId());
				Sponsorship sponsorship;
				if (col.size() > 0) {
					sponsorship = super.randomizeSponsorships(col);
					result.addObject("linkBanner", sponsorship.getBanner());
					sponsorship.setFlat_rate(sponsorship.getFlat_rate() + this.sponsorshipService.getFlatRate());
				}
			} else
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
			result = super.save(position, binding, "position.commit.error", "position/edit", "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
		else
			result = super.createAndEditModelAndView(position, "deadline.future", "position/edit", "position/company/edit.do", "/position/company/list.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position) {
		ModelAndView result;
		result = super.delete(position, "position.commit.error", "position/edit", "position/company/edit.do", "/position/company/list.do", "redirect:list.do");
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
		Rookie h;

		try {
			h = (Rookie) this.service.findByUserAccount(LoginService.getPrincipal().getId());
			finder = h.getFinder();
			this.oldRookieFinder = finder;

		} catch (final IllegalArgumentException e) {
			finder = this.serviceFinder.create();
		} catch (final ClassCastException e) {
			finder = this.serviceFinder.create();
		}

		result = super.create(finder, "position/find", "position/search.do", "/");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(Finder finder, final BindingResult binding) {

		ModelAndView result = null;
		Rookie h;
		h = null;

		Collection<Position> res;
		res = new ArrayList<Position>();

		try {
			h = (Rookie) this.service.findByUserAccount(LoginService.getPrincipal().getId());
			finder = this.serviceFinder.reconstruct(finder, binding);

			if (this.checkFinderProperties(this.oldRookieFinder, finder) && this.checkFinderTime(finder))
				res = h.getFinder().getPositions();
			else if (finder.getSingleKey().equals("") && finder.getDeadline() == null && finder.getMinSalary() == null && finder.getMaxSalary() == null) {
				res = this.service.getPublicPositions();
				this.serviceFinder.save(finder, res);
			} else {
				res = this.serviceFinder.searchWithRetain(finder.getSingleKey(), finder.getDeadline(), finder.getMinSalary(), finder.getMaxSalary());
				this.serviceFinder.save(finder, res);
			}
			result = super.listModelAndView("positions", "position/list", res, "position/list.do");

		} catch (final ValidationException e) {
			result = super.createAndEditModelAndView(finder, "position/find", "position/search.do", "/");
		} catch (final ClassCastException cce) {
			if (finder.getSingleKey().equals(""))
				res = this.service.getPublicPositions();
			else
				res = this.serviceFinder.findBySingleKey(finder.getSingleKey());
			result = super.listModelAndView("positions", "position/list", res, "position/list.do");
		} catch (final Throwable oops) {
			if (finder.getSingleKey().equals(""))
				res = this.service.getPublicPositions();
			else
				res = this.serviceFinder.findBySingleKey(finder.getSingleKey());
			result = super.listModelAndView("positions", "position/list", res, "position/list.do");
		}
		return result;
	}
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public ModelAndView clearFinder() {
		ModelAndView result;
		Rookie h;
		Finder aux;
		h = (Rookie) this.service.findByUserAccount(LoginService.getPrincipal().getId());
		aux = h.getFinder();
		aux = this.serviceFinder.clear(aux);
		result = super.edit(aux, "position/find", "position/search.do", "redirect:../welcome.do");
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Position p;
		p = (Position) e;
		p = this.service.reconstruct(p, binding);
		Position position;
		position = this.service.save(p);
		if (position.isFinalMode())
			this.serviceMess.sendNotification(position);
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;

		//		Collection<Problem> col;
		//		col = this.problemService.getProblemByPositionId(((Position) e).getId());
		//
		//		for (final Problem problem : col) {
		//			Collection<Position> colP;
		//			colP = problem.getPosition();
		//			colP.remove(e);
		//			problem.setPosition(colP);
		//		}

		this.service.delete(((Position) e).getId());
		result = new ModelAndView(nameResolver);
		return result;
	}
	private boolean checkFinderTime(final Finder finder) {
		final Date creationFinder = finder.getCreationDate();
		final Date now = new Date();
		boolean useCache = true;
		final long diff = now.getTime() - creationFinder.getTime();
		final long diffMinutes = diff / (60 * 1000);

		if (diffMinutes >= Integer.valueOf(System.getProperty("hoursFinder")) * 60)
			useCache = false;

		return useCache;
	}

	private boolean checkFinderProperties(final Finder f, final Finder search) {
		boolean res = true;
		if (f.getSingleKey() != null)
			res &= f.getSingleKey().equals(search.getSingleKey());
		if (f.getDeadline() != null)
			res &= f.getDeadline().equals(search.getDeadline());
		if (f.getMinSalary() != null)
			res &= f.getMinSalary().equals(search.getMinSalary());
		if (f.getMaxSalary() != null)
			res &= f.getMaxSalary().equals(search.getMaxSalary());
		return res;
	}
}
