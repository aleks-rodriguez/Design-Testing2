
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomisationSystemRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Actor;
import domain.CustomisationSystem;

@Service
@Transactional
public class CustomisationSystemService {

	@Autowired
	private CustomisationSystemRepository	repositoryCustomisationSystem;


	public CustomisationSystem findUnique() {
		return this.repositoryCustomisationSystem.findAll().get(0);
	}
	public Collection<Actor> findAllNoEnabledActors() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repositoryCustomisationSystem.findAllNoEnabledActors();
	}

	public CustomisationSystem save(final CustomisationSystem custom) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repositoryCustomisationSystem.save(custom);
	}
	public void banActor(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repositoryCustomisationSystem.findActor(id);
		a.getAccount().setEnabled(false);
	}

	public void unBanActor(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repositoryCustomisationSystem.findActor(id);
		a.getAccount().setEnabled(true);
	}
}
