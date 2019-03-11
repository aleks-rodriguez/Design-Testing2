
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

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

		if (proclaim.getId() == 0)
			result = proclaim;
		else {
			result = this.repositoryProclaim.findOne(proclaim.getId());
			result.setFinalMode(proclaim.isFinalMode());
			result.setMoment(new Date());
			result.setText(proclaim.getText());
		}

		return result;
	}

	public Proclaim save(final Proclaim proclaim) {

		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER));

		final Actor a = this.repositoryProclaim.findByUserAccount(LoginService.getPrincipal().getId());
		Chapter c;
		c = (Chapter) a;

		if (proclaim.getId() != 0)
			Assert.isTrue(c.getProclaims().contains(proclaim));

		Proclaim saved;
		saved = this.repositoryProclaim.save(proclaim);

		Collection<Proclaim> proclaims;
		proclaims = c.getProclaims();

		if (!proclaims.contains(saved)) {
			proclaims.add(saved);
			c.setProclaims(proclaims);
		}

		return saved;
	}

	public void deleteProclaim(final int id) {

		final UserAccount ua = LoginService.getPrincipal();
		final Actor a = this.repositoryProclaim.findByUserAccount(ua.getId());
		Chapter c = null;

		if (a != null)
			c = (Chapter) a;

		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER));
		final Proclaim pro = this.repositoryProclaim.findOne(id);
		Assert.isTrue(c.getProclaims().contains(pro) && !pro.isFinalMode());

		Collection<Proclaim> proclaims;
		proclaims = c.getProclaims();

		if (!proclaims.contains(pro)) {
			proclaims.add(pro);
			c.setProclaims(proclaims);
			this.repositoryProclaim.delete(id);
		}

	}
}
