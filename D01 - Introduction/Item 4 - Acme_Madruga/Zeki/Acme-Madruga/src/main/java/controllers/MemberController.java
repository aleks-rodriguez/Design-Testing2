
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.MemberService;
import domain.Member;

@Controller
@RequestMapping(value = {
	"/member"
})
public class MemberController {

	@Autowired
	private MemberService	memberService;


	// Constructors -----------------------------------------------------------

	public MemberController() {
		super();
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model;

		model = this.createEditModelAndView(this.memberService.createMember());

		return model;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(defaultValue = "false") final boolean check) {
		ModelAndView result;

		result = new ModelAndView("member/edit");
		result.addObject("view", true);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(member);
			result.addObject("errors", binding.getAllErrors());
		} else
			try {
				this.memberService.save(member);

				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(member, "member.commit.error");
				result.addObject("oops", oops.getMessage());
				result.addObject("errors", binding.getAllErrors());
			}
		return result;

	}

	// Update personal data
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonalData() {
		ModelAndView result;

		Member find;

		find = this.memberService.findByUserAccount(LoginService.getPrincipal().getId());

		result = this.createEditModelAndView(find);
		result.addObject("view", false);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView model;
		model = this.createEditModelAndView(member, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final Member member, final String message) {
		ModelAndView result;
		result = new ModelAndView("member/edit");

		result.addObject("member", member);
		result.addObject("message", message);
		if (member.getId() == 0)
			result.addObject("view", false);

		return result;
	}

}
