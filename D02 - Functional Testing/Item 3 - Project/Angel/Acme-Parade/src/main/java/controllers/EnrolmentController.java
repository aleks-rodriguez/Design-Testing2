
package controllers;

import java.util.Collection;
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
import services.MessageService;
import services.PositionService;
import services.ParadeService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping(value = {
	"/enrolment/member", "/enrolment/brotherhood", "/enrolment"
})
public class EnrolmentController extends AbstractController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ParadeService	processionService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


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
		this.enrolmentService.saveEnrolment(e);
		result = this.custom(new ModelAndView("redirect:/enrolment/member/listBrotherhood.do"));
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
				enrolment = this.enrolmentService.reconstructEnrolment(enrolment, binding);
				this.enrolmentService.saveEnrolment(enrolment);
				if (enrolment.getId() == 0)
					result = this.custom(new ModelAndView("redirect:enrolment/member/list.do"));
				else {
					this.messageService.createMessageNotifyEnrolAMember(enrolment.getMember(), enrolment.getBrotherhood());
					result = this.custom(new ModelAndView("redirect:./list.do"));
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}
	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idEnrolment) {
		ModelAndView result;
		Brotherhood b;
		b = (Brotherhood) this.actorService.findByUserAccount();
		Assert.isTrue(b.getArea() != null, "A brotherhood must have an area selected");
		Enrolment e;
		e = this.enrolmentService.findOneEnrolment(idEnrolment);
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
			this.enrolmentService.deleteEnrolment(e.getId());
			this.enrolmentService.saveEnrolment(e);
			result = this.custom(new ModelAndView("redirect:/enrolment/member/listOwn.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOneEnrolment(e.getId()), "enrolment.commit.error");
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
			this.enrolmentService.deleteEnrolment(e.getId());
			this.enrolmentService.saveEnrolment(e);
			this.messageService.createMessageNotifyDropOutBrotherhood(this.actorService.findOneMember(idMember), b);
			result = this.custom(new ModelAndView("redirect:/enrolment/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOneEnrolment(e.getId()), "enrolment.commit.error");
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
			this.enrolmentService.readmiteEnrolment(e.getId());
			this.enrolmentService.saveEnrolment(e);
			result = this.custom(new ModelAndView("redirect:/enrolment/member/listDelete.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.enrolmentService.findOneEnrolment(e.getId()), "enrolment.commit.error");
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

	@RequestMapping(value = "/listBrotherhood", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.MEMBER));
		Member m;
		m = this.actorService.findMemberByUser(user.getId());
		result = this.custom(new ModelAndView("brotherhood/list"));
		Collection<Brotherhood> b;
		b = this.actorService.findAllBrotherhood();
		b.removeAll(this.actorService.getAllBrotherhoodActiveByMemberId(m.getId()));
		b.removeAll(this.actorService.getAllBrotherhoodDisByMemberId(m.getId()));
		result.addObject("brotherhoods", b);
		result.addObject("requestURI", "enrolment/member/listBrotherhood.do");
		result.addObject("every", true);
		return result;
	}

	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView listOwn() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.MEMBER));
		Member m;
		m = this.actorService.findMemberByUser(user.getId());
		result = this.custom(new ModelAndView("brotherhood/list"));
		result.addObject("brotherhoods", this.actorService.getAllBrotherhoodActiveByMemberId(m.getId()));
		result.addObject("requestURI", "enrolment/member/listOwn.do");
		result.addObject("own", true);
		return result;
	}

	@RequestMapping(value = "/listDelete", method = RequestMethod.GET)
	public ModelAndView listDelete() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.MEMBER));
		Member m;
		m = this.actorService.findMemberByUser(user.getId());
		result = this.custom(new ModelAndView("brotherhood/list"));
		result.addObject("brotherhoods", this.actorService.getAllBrotherhoodDisByMemberId(m.getId()));
		result.addObject("requestURI", "enrolment/member/listDelete.do");
		result.addObject("dis", true);
		return result;
	}

	@RequestMapping(value = "/listMember", method = RequestMethod.GET)
	public ModelAndView listMember(@RequestParam(defaultValue = "0") final int idBrotherhood) {
		ModelAndView result;
		result = this.custom(new ModelAndView("member/list"));
		Collection<Member> m;
		if (idBrotherhood == 0) {
			UserAccount user;
			user = LoginService.getPrincipal();
			Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
			Brotherhood b;
			b = (Brotherhood) this.actorService.findByUserAccount(user.getId());
			m = this.actorService.getAllMemberByBrotherhood(b.getId());
			result.addObject("members", m);
		} else {
			m = this.actorService.getAllMemberByBrotherhood(idBrotherhood);
			result.addObject("members", m);
			result.addObject("requestURI", "enrolment/brotherhood/listMember.do?idBrotherhood=" + idBrotherhood);
		}

		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idMember) {
		final ModelAndView result;
		Member m;
		m = this.actorService.findOneMember(idMember);
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "enrolment/brotherhood/show.do?idMember=" + m.getId());
		result.addObject("view", true);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member) {
		return this.createEditModelAndView(member, null);
	}

	protected ModelAndView createEditModelAndView(final Member member, final String message) {
		ModelAndView result;
		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(member.getId()));
		result = this.editFormsUrlId("enrolment/brotherhood/edit.do", map, "/enrolment/brotherhood/list.do", this.custom(new ModelAndView("actor/edit")));
		result.addObject("check", false);
		result.addObject("actor", member);
		result.addObject("message", message);
		return result;
	}

}
