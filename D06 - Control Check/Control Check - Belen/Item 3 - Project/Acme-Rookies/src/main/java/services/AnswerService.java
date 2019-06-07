
package services;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnswerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Answer;
import domain.Rookie;

@Service
@Transactional
public class AnswerService extends AbstractService {

	@Autowired
	private AnswerRepository	answerRepository;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private Validator			validator;


	public Answer findOne(final int idAnswer) {
		return this.answerRepository.findOne(idAnswer);
	}

	public Answer createAnswer(final int idApplication) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		Rookie h;
		h = (Rookie) this.applicationService.findActorByUserAccountId(user.getId());
		Answer a;
		Assert.isTrue(this.applicationService.getApplicationByRookieId(h.getId()).contains(this.applicationService.findOne(idApplication)), "You don't have permission to do this");
		a = new Answer();
		a.setLink("");
		a.setExplanation("");
		return a;
	}

	public Answer save(final Answer a) {
		Rookie h;
		h = (Rookie) this.applicationService.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Answer saved;
		saved = this.answerRepository.save(a);
		return saved;
	}

	public Answer reconstruct(final Answer a, final BindingResult binding) {
		Answer result;
		result = a;
		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

}
