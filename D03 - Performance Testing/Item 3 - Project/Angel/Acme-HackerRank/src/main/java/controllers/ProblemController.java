
package controllers;

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
import services.PositionService;
import services.ProblemService;
import domain.Company;
import domain.DomainEntity;
import domain.Problem;

@Controller
@RequestMapping(value = {
	"/problem/company", "/problem/hacker"
})
public class ProblemController extends BasicController {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return super.listModelAndView("problems", "problem/list", this.problemService.getProblemByCompanyId(this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId()), "problem/company/list.do");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = super.create(this.problemService.createProblem(), "problem", "problem/edit", "problem/company/edit.do", "/problem/company/list.do");
		result.addObject("position", this.positionService.getPositionsByCompany(this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId()));
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Company a;
		a = this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId());

		Problem problem;
		problem = this.problemService.findOne(id);

		Collection<Problem> col;
		col = this.problemService.getProblemByCompanyId(a.getId());

		Assert.isTrue(col.contains(problem), "You don't have permission to do this");
		String requestCancel = "";

		if (this.positionService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			requestCancel = "/problem/company/list.do";

		ModelAndView result;
		result = super.edit(this.problemService.findOne(id), "problem", "problem/edit", "problem/company/edit.do", requestCancel);
		if (this.positionService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			result.addObject("position", this.positionService.getPositionsByCompany(this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId()));
		result.addObject("position2", this.problemService.findOne(id).getPosition());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;

		String requestCancel = "";
		if (this.positionService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			requestCancel = "/problem/company/list.do";
		else
			requestCancel = "/application/hacker/list.do";

		result = super.show(this.problemService.findOne(id), "problem", "problem/edit", "problem/company/edit.do", requestCancel);
		if (this.positionService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			result.addObject("position", this.positionService.getPositionsByCompany(this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId()));
		result.addObject("position2", this.problemService.findOne(id).getPosition());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEntity(final Problem problem, final BindingResult binding) {
		ModelAndView result = null;
		if (super.checkURL(problem.getAttachments(), true))
			result = super.save(problem, binding, "problem.commit.error", "problem", "problem/edit", "problem/company/edit.do", "redirect:list.do", "redirect:list.do");
		else
			result = super.createAndEditModelAndView(problem, "bad.url", "problem", "problem/edit", "problem/company/edit.do", "redirect:list.do");
		result.addObject("position", this.positionService.getPublicPositionsByCompany(this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId()));
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteEntity(@RequestParam final int id) {
		Company a;
		a = this.problemService.findCompanyByUserAccountId(LoginService.getPrincipal().getId());

		Problem problem;
		problem = this.problemService.findOne(id);

		Collection<Problem> col;
		col = this.problemService.getProblemByCompanyId(a.getId());

		Assert.isTrue(col.contains(problem), "You don't have permission to do this");
		ModelAndView result;
		result = super.delete(this.problemService.findOne(id), "problem.commit.error", "problem", "problem/edit", "problem/edit.do", "redirect:list.do", "redirect:list.do");
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Problem problem;
		problem = (Problem) e;
		problem = this.problemService.reconstruct(problem, binding);
		this.problemService.save(problem);
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		Problem problem;
		problem = (Problem) e;
		this.problemService.delete(problem.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
