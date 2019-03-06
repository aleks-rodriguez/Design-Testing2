
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.EnrolmentService;
import services.PositionService;
import services.ProcessionService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping(value = {
	"/enrolment/member", "/enrolment/brotherhood"
})
public class EnrolmentController extends AbstractController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listEnrolment() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Brotherhood b;
		b = this.processionService.findBrotherhoodByUser(user.getId());
		result = this.custom(new ModelAndView("enrolment/list"));
		result.addObject("enrolments", this.enrolmentService.findEnrolmentWithoutPosition(b.getId()));
		result.addObject("requestURI", "enrolment/brotherhood/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idBrotherhood) {
		ModelAndView result;
		Enrolment e;
		e = this.enrolmentService.createEnrolment(idBrotherhood);
		this.enrolmentService.save(e);
		result = this.custom(new ModelAndView("redirect:/brotherhood/member/list.do"));
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idEnrolment, @ModelAttribute("enrolment") Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				enrolment = this.enrolmentService.reconstruct(enrolment, binding);
				this.enrolmentService.save(enrolment);
				if (enrolment.getId() == 0)
					result = this.custom(new ModelAndView("redirect:brotherhood/member/list.do"));
				else
					result = this.custom(new ModelAndView("redirect:./list.do"));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}
	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idEnrolment) {
		ModelAndView result;
		Enrolment e;
		e = this.enrolmentService.findOne(idEnrolment);
		result = this.createEditModelAndView(e);
		result.addObject("position", this.positionService.findAll());
		result.addObject("idEnrolment", idEnrolment);
		return result;
	}
	//Delete brotherhood
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idBrotherhood) {
		ModelAndView result = null;
		UserAccount user;
		user = LoginService.getPrincipal();
		Member m;
		m = this.actorService.findMemberByUser(user.getId());
		final Enrolment e;
		e = this.actorService.getEnrolmentByMemberAndBrother(m.getId(), idBrotherhood);
		try {
			this.enrolmentService.delete(e.getId());
			this.enrolmentService.save(e);
			result = this.custom(new ModelAndView("redirect:/brotherhood/member/listOwn.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOne(e.getId()), "enrolment.commit.error");
		}
		return result;
	}

	//Delete member
	@RequestMapping(value = "/deleteMember", method = RequestMethod.GET)
	public ModelAndView deleteMember(@RequestParam final int idMember) {
		ModelAndView result = null;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = (Brotherhood) this.actorService.findByUserAccount(user.getId());
		final Enrolment e;
		e = this.actorService.getEnrolmentByMemberAndBrother(idMember, b.getId());
		try {
			this.enrolmentService.delete(e.getId());
			this.enrolmentService.save(e);
			result = this.custom(new ModelAndView("redirect:/member/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOne(e.getId()), "enrolment.commit.error");
		}
		return result;
	}
	//Readmite
	@RequestMapping(value = "/readmite", method = RequestMethod.GET)
	public ModelAndView readmite(@RequestParam final int idBrotherhood) {
		UserAccount user;
		user = LoginService.getPrincipal();
		ModelAndView result = null;
		Member m;
		m = this.actorService.findMemberByUser(user.getId());
		final Enrolment e;
		e = this.actorService.getEnrolmentByMemberAndBrother(m.getId(), idBrotherhood);
		try {
			this.enrolmentService.readmite(e.getId());
			this.enrolmentService.save(e);
			result = this.custom(new ModelAndView("redirect:/brotherhood/member/listDelete.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOne(e.getId()), "enrolment.commit.error");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Enrolment enrolment) {
		return this.createEditModelAndView(enrolment, null);
	}

	protected ModelAndView createEditModelAndView(final Enrolment enrolment, final String message) {
		ModelAndView result;
		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(enrolment.getId()));
		result = this.editFormsUrlId("enrolment/brotherhood/edit.do", map, "/enrolment/brotherhood/list.do", this.custom(new ModelAndView("enrolment/edit")));
		result.addObject("enrolment", enrolment);
		result.addObject("message", message);
		return result;
	}

}
