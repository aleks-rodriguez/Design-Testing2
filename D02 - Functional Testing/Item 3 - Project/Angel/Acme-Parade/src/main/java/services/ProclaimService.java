
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProclaimRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Actor;
import domain.Chapter;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {

	@Autowired
	private ProclaimRepository	repositoryProclaim;

	@Autowired(required = false)
	private Validator			validator;


	public Collection<Proclaim> findProclaimsByChapter(final int id) {
		return this.repositoryProclaim.findAllProclaimsByChapter(id);
	}

	public Proclaim createProclaim() {
		Proclaim proclaim;
		proclaim = new Proclaim();

		proclaim.setText("");
		proclaim.setMoment(new Date());
		proclaim.setFinalMode(false);
		return proclaim;
	}

	public Proclaim findOne(final int id) {
		return this.repositoryProclaim.findOne(id);
	}

	public Proclaim reconstruct(final Proclaim proclaim, final BindingResult binding) {

		Proclaim result;

		if (proclaim.getId() == 0) {
			result = proclaim;
			final Actor a = this.repositoryProclaim.findByUserAccount(LoginService.getPrincipal().getId());
			final Chapter c = (Chapter) a;
			result.setChapter(c);
		} else {
			result = this.repositoryProclaim.findOne(proclaim.getId());
			result.setFinalMode(proclaim.isFinalMode());
			result.setMoment(new Date());
			result.setText(proclaim.getText());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Proclaim save(final Proclaim proclaim) {

		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER));

		final Actor a = this.repositoryProclaim.findByUserAccount(LoginService.getPrincipal().getId());
		Chapter c;
		c = (Chapter) a;

		Proclaim saved = null;

		if (proclaim.getChapter().equals(c))
			saved = this.repositoryProclaim.save(proclaim);

		return saved;
	}

	public void deleteProclaim(final int id) {

		final UserAccount ua = LoginService.getPrincipal();
		final Actor a = this.repositoryProclaim.findByUserAccount(ua.getId());
		final Chapter c = (Chapter) a;
		final Proclaim proclaim = this.repositoryProclaim.findOne(id);
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER));
		Assert.isTrue(proclaim.getChapter().equals(c));
		this.repositoryProclaim.delete(id);

	}
}
