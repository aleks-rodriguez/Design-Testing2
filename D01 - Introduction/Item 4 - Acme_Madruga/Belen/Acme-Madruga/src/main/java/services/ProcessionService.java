
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	ProcessionRepository	processionRepository;


	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession findOne(final int idProcession) {
		return this.processionRepository.findOne(idProcession);
	}

	public Procession createProcession() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Brotherhood b;
		b = this.processionRepository.findBrotherhoodByUserAccountId(user.getId());
		Procession p;
		p = new Procession();
		p.setTicker(Utiles.generateTicker());
		p.setTitle("");
		p.setDescription("");
		p.setMomentOrganised(new Date());
		p.setFinalMode(false);
		p.setRequests(new ArrayList<Request>());
		p.setFloats(b.getFloats());
		return p;
	}
	public Procession save(final Procession procession) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Procession saved;
		saved = this.processionRepository.save(procession);
		return saved;
	}

	public void delete(final int idProcession) {
		Assert.notNull(idProcession);
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Procession p;
		p = this.processionRepository.findOne(idProcession);
		this.processionRepository.delete(p);
	}
}
