
package controllers;

import java.util.Date;

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
import services.AnswerService;
import services.ApplicationService;
import domain.Answer;
import domain.Application;
import domain.DomainEntity;
import domain.Hacker;

@Controller
@RequestMapping(value = {
	"/answer/hacker", "/answer/company"
})
public class AnswerController extends BasicController {

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private ApplicationService	applicationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idApplication) {
		ModelAndView result;
		UserAccount user;
		user = LoginService.getPrincipal();
		Hacker h;
		h = (Hacker) this.applicationService.findActorByUserAccountId(user.getId());
		Assert.isTrue(this.applicationService.getApplicationByHackerId(h.getId()).contains(this.applicationService.findOne(idApplication)), "You don't have permission to do this");
		result = super.create(this.answerService.createAnswer(idApplication), "answer", "answer/edit", "answer/hacker/edit.do?idApplication=" + idApplication, "/application/hacker/list.do");
		result.addObject("idApplication", idApplication);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idApplication) {
		ModelAndView result;

		Application app;
		app = this.applicationService.findOne(idApplication);
		Answer a;
		a = app.getAnswer();

		String requestCancel = "";
		if (this.answerService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.HACKER))
			requestCancel = "/application/hacker/list.do";
		else if (this.answerService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			requestCancel = "/application/company/list.do?position=" + app.getPosition().getId();

		result = super.show(a, "answer", "answer/edit", "answer/hacker/edit.do", requestCancel);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int idApplication, final Answer a, final BindingResult binding) {
		ModelAndView result = null;
		Application app;
		result = super.save(a, binding, "answer.commit.error", "answer", "answer/edit", "answer/hacker/edit.do?idApplication=" + idApplication, "/application/hacker/list.do", "redirect:../../application/hacker/list.do");
		Answer saved;
		saved = (Answer) result.getModel().get("saved");
		app = this.applicationService.findOne(idApplication);
		if (binding.getAllErrors().size() == 0) {
			app.setMoment(new Date());
			app.setStatus("SUBMITTED");
			app.setAnswer(saved);
			this.applicationService.save(app);
		} else
			result.addObject("idApplication", idApplication);
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Answer answer;
		answer = (Answer) e;
		answer = this.answerService.reconstruct(answer, binding);
		Answer saved;
		saved = this.answerService.save(answer);
		result = new ModelAndView(nameResolver);
		result.addObject("saved", saved);
		return result;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		return null;
	}

}
