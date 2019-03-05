
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.BrotherhoodService;
import services.MemberService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping(value = {
	"/member/brotherhood"
})
public class MemberController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Brotherhood b;
		b = (Brotherhood) this.brotherhoodService.findByUserAccount(user.getId());
		result = new ModelAndView("member/list");
		Collection<Member> m;
		m = this.brotherhoodService.getAllMemberByBrotherhood(b.getId());
		result.addObject("members", m);
		result.addObject("requestURI", "member/brotherhood/list.do");
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idMember) {
		final ModelAndView result;
		final Member m;
		m = this.memberService.findOne(idMember);
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "member/brotherhood/show.do?idProcession=" + m.getId());
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
		result = this.editFormsUrlId(member, "member/brotherhood/edit.do", map, "/member/brotherhood/list.do", this.custom(new ModelAndView("member/edit")));
		result.addObject("member", member);
		result.addObject("message", message);
		return result;
	}

}
