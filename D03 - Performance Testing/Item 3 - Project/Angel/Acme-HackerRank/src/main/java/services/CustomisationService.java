
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomisationSystemRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.CustomisationSystem;

@Service
@Transactional
public class CustomisationService extends AbstractService {

	@Autowired
	CustomisationSystemRepository	repository;


	public void flagSpam(final int idActor) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActorByUserAccountId(idActor);
		a.setSpammer(true);
	}
	public CustomisationSystem findUnique() {
		return this.repository.findAll().get(0);
	}
	public Collection<Actor> findAllNoEnabledActors() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repository.findAllNoEnabledActors();
	}

	public CustomisationSystem save(final CustomisationSystem custom) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repository.save(custom);
	}
	public void banActor(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActorByUserAccountId(id);
		a.getAccount().setEnabled(false);
	}

	public void unBanActor(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActorByUserAccountId(id);
		a.getAccount().setEnabled(true);
	}

}
