
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.BrotherhoodService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping(value = {
	"/brotherhood/member"
})
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.MEMBER));
		Member m;
		m = this.brotherhoodService.findMemberByUser(user.getId());
		result = new ModelAndView("brotherhood/list");
		Collection<Brotherhood> b;
		b = this.brotherhoodService.findAll();
		b.removeAll(this.brotherhoodService.getAllBrotherhoodActiveByMemberId(m.getId()));
		result.addObject("brotherhoods", b);
		result.addObject("requestURI", "brotherhood/member/list.do");
		result.addObject("own", false);
		return result;
	}

	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView listOwn() {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.MEMBER));
		Member m;
		m = this.brotherhoodService.findMemberByUser(user.getId());
		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", this.brotherhoodService.getAllBrotherhoodActiveByMemberId(m.getId()));
		result.addObject("requestURI", "brotherhood/member/list.do");
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
		m = this.brotherhoodService.findMemberByUser(user.getId());
		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", this.brotherhoodService.getAllBrotherhoodDisByMemberId(m.getId()));
		result.addObject("requestURI", "brotherhood/member/list.do");
		result.addObject("dis", true);
		return result;
	}

}
