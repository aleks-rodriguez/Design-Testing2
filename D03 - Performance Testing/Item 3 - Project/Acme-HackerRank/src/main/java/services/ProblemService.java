
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.LoginService;
import domain.Company;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;
	@Autowired
	private Validator			validator;


	public Problem findOne(final int id) {
		return this.problemRepository.findOne(id);
	}

	public Company findCompanyByUserAccountId(final int id) {
		return (Company) this.problemRepository.findCompanyByUserAccountId(id);
	}

	public Collection<Problem> getProblemByCompanyId(final int id) {
		return this.problemRepository.getProblemByCompanyId(id);
	}

	public Problem createProblem() {
		Problem result;
		result = new Problem();
		result.setTitle("");
		result.setStatement("");
		result.setHint("");
		result.setAttachments(new ArrayList<String>());
		result.setFinalMode(false);
		result.setCompany(new Company());
		return result;
	}

	public Problem save(final Problem p) {
		Company a;
		a = (Company) this.problemRepository.findCompanyByUserAccountId(LoginService.getPrincipal().getId());

		Collection<Problem> col;
		col = this.problemRepository.getProblemByCompanyId(a.getId());

		Problem modify;

		if (p.getId() == 0)
			modify = this.problemRepository.save(p);
		else {
			Assert.isTrue(col.contains(p));
			modify = this.problemRepository.save(p);
		}

		return modify;
	}

	public void delete(final int id) {
		Company a;
		a = (Company) this.problemRepository.findCompanyByUserAccountId(LoginService.getPrincipal().getId());

		Problem problem;
		problem = this.problemRepository.findOne(id);

		Collection<Problem> col;
		col = this.problemRepository.getProblemByCompanyId(a.getId());

		Assert.isTrue(col.contains(problem));

		if (col.contains(problem))
			this.problemRepository.delete(problem);
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		Problem result;
		if (problem.getId() == 0) {
			result = problem;
			result.setCompany((Company) this.problemRepository.findCompanyByUserAccountId(LoginService.getPrincipal().getId()));
		} else {
			result = this.problemRepository.findOne(problem.getId());
			result.setPosition(problem.getPosition());
			result.setFinalMode(problem.getFinalMode());
			result.setStatement(problem.getStatement());
			result.setHint(problem.getHint());
			result.setAttachments(problem.getAttachments());
			result.setTitle(problem.getTitle());
		}
		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}
}
