
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	@Autowired(required = false)
	private Validator		validator;

	@Autowired
	ProcessionRepository	processionRepository;


	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession findOne(final int idProcession) {
		return this.processionRepository.findOne(idProcession);
	}

	public Brotherhood findBrotherhoodByUser(final int userId) {
		return this.processionRepository.findBrotherhoodByUserAccountId(userId);
	}

	public Brotherhood findBrotherhoodByProcession(final int idProcession) {
		return this.processionRepository.findBrotherhoodByProcessionId(idProcession);
	}

	public Procession createProcession() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Procession p;
		p = new Procession();
		p.setTicker(Utiles.generateTicker());
		p.setTitle("");
		p.setDescription("");
		p.setMomentOrganised(new Date());
		p.setFinalMode(false);
		p.setRequests(new ArrayList<Request>());
		p.setFloats(new ArrayList<Float>());
		return p;
	}
	public Procession save(final Procession procession) {
		UserAccount user;
		user = LoginService.getPrincipal();
		//		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Procession saved;
		saved = this.processionRepository.save(procession);
		Brotherhood b;
		b = this.processionRepository.findBrotherhoodByUserAccountId(user.getId());
		Collection<Procession> processionPerBrotherhood;
		processionPerBrotherhood = b.getProcessions();
		if (!processionPerBrotherhood.contains(saved)) {
			processionPerBrotherhood.add(saved);
			b.setProcessions(processionPerBrotherhood);
		}
		return saved;
	}

	public void delete(final int idProcession) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Procession p;
		p = this.processionRepository.findOne(idProcession);
		Brotherhood b;
		b = this.processionRepository.findBrotherhoodByProcessionId(idProcession);
		Collection<Procession> processionPerBrotherhood;
		processionPerBrotherhood = b.getProcessions();
		processionPerBrotherhood.remove(p);
		b.setProcessions(processionPerBrotherhood);
		this.processionRepository.delete(p);
	}

	public Procession reconstruct(final Procession procession, final BindingResult binding) {
		Procession result;
		if (procession.getId() == 0)
			result = procession;
		else {
			result = this.processionRepository.findOne(procession.getId());

			result.setTicker(procession.getTicker());
			result.setTitle(procession.getTitle());
			result.setDescription(procession.getDescription());
			result.setFinalMode(procession.getFinalMode());
			result.setFloats(procession.getFloats());
			result.setRequests(procession.getRequests());
		}
		this.validator.validate(result, binding);
		return result;
	}
}
