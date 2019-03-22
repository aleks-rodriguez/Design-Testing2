
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
import domain.Chapter;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {

	@Autowired
	private ProclaimRepository	repositoryProclaim;

	@Autowired
	private Validator			validator;


	public Chapter findByUA(final int id) {
		return this.repositoryProclaim.findByUserAccount(id);
	}

	public Collection<Proclaim> findProclaimsByChapter(final int id) {
		return this.repositoryProclaim.findAllProclaimsByChapter(id);
	}
	public Collection<Proclaim> findProclaimsByChapterFinalMode(final int id) {
		return this.repositoryProclaim.findAllProclaimsByChapterFinalMode(id);
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

		if (proclaim.getId() == 0)
			result = proclaim;
		else {
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

		Chapter c;
		c = this.repositoryProclaim.findByUserAccount(LoginService.getPrincipal().getId());

		if (proclaim.getId() == 0)
			proclaim.setChapter(c);

		Proclaim saved = null;

		if (proclaim.getChapter().equals(c))
			saved = this.repositoryProclaim.save(proclaim);
		else
			throw new IllegalArgumentException();
		return saved;
	}

	public void deleteProclaim(final int id) {

		final UserAccount ua = LoginService.getPrincipal();
		final Chapter c = this.repositoryProclaim.findByUserAccount(ua.getId());
		final Proclaim proclaim = this.repositoryProclaim.findOne(id);
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER));
		Assert.isTrue(proclaim.getChapter().equals(c));
		Assert.isTrue(!proclaim.isFinalMode());
		this.repositoryProclaim.delete(id);

	}
}
