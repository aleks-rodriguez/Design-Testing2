
package controllers;

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
		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", this.brotherhoodService.findAll());
		result.addObject("requestURI", "brotherhood/member/list.do");
		return result;
	}
}
