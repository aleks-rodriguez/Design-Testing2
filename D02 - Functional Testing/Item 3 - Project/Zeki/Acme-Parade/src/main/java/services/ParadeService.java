
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

import repositories.ParadeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Float;
import domain.Parade;
import domain.Request;

@Service
@Transactional
public class ParadeService {

	@Autowired(required = false)
	private Validator	validator;

	@Autowired
	ParadeRepository	processionRepository;


	public Collection<Parade> findAll() {
		return this.processionRepository.findAll();
	}

	public Parade findOne(final int idParade) {
		return this.processionRepository.findOne(idParade);
	}

	public Brotherhood findBrotherhoodByUser(final int userId) {
		return this.processionRepository.findBrotherhoodByUserAccountId(userId);
	}

	public Brotherhood findBrotherhoodByParade(final int idParade) {
		return this.processionRepository.findBrotherhoodByParadesId(idParade);
	}

	public Collection<Parade> findParadeByBrotherhoodId(final int idBrotherhood) {
		return this.processionRepository.findParadeByBrotherhoodId(idBrotherhood);
	}

	public Parade createParade() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Parade p;
		p = new Parade();
		p.setTicker(Utiles.generateTicker());
		p.setTitle("");
		p.setDescription("");
		p.setMomentOrganised(new Date());
		p.setFinalMode(false);
		p.setRequests(new ArrayList<Request>());
		p.setFloats(new ArrayList<Float>());
		p.setStatus("");
		p.setWhyRejected("");
		return p;
	}
	public Parade save(final Parade parade) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Parade saved;
		saved = this.processionRepository.save(parade);
		Brotherhood b;
		b = this.processionRepository.findBrotherhoodByUserAccountId(user.getId());
		Collection<Parade> parades;
		parades = b.getParades();
		if (!parades.contains(saved)) {
			parades.add(saved);
			b.setParades(parades);
		}
		return saved;
	}

	public void delete(final int idParade) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Parade p;
		p = this.processionRepository.findOne(idParade);
		Brotherhood b;
		b = this.processionRepository.findBrotherhoodByParadesId(idParade);
		Collection<Parade> paradesPerBrotherhood;
		paradesPerBrotherhood = b.getParades();
		if (paradesPerBrotherhood.contains(p)) {
			paradesPerBrotherhood.remove(p);
			b.setParades(paradesPerBrotherhood);
			this.processionRepository.delete(p);
		}
	}

	public Parade reconstruct(final Parade parade, final BindingResult binding) {
		Parade result;
		if (parade.getId() == 0) {
			result = parade;
			result.setStatus(parade.getStatus());
		} else {
			result = this.processionRepository.findOne(parade.getId());

			result.setTicker(parade.getTicker());
			result.setTitle(parade.getTitle());
			result.setDescription(parade.getDescription());
			result.setFinalMode(parade.getFinalMode());
			result.setFloats(parade.getFloats());
			result.setRequests(parade.getRequests());
			result.setWhyRejected(parade.getWhyRejected());
			result.setStatus(parade.getStatus());

		}
		this.validator.validate(result, binding);
		return result;
	}

	public Parade copyParade(final int id) {

		Parade toCopy;
		toCopy = this.processionRepository.findOne(id);

		Parade newParade;
		newParade = this.createParade();

		newParade.setDescription(toCopy.getDescription());
		newParade.setFinalMode(false);
		newParade.setFloats(toCopy.getFloats());
		newParade.setMomentOrganised(toCopy.getMomentOrganised());
		newParade.setRequests(toCopy.getRequests());
		newParade.setTicker(Utiles.generateTicker());
		newParade.setStatus("");
		newParade.setWhyRejected("");

		Parade saved;
		saved = this.processionRepository.save(newParade);

		Brotherhood b;
		b = this.findBrotherhoodByParade(toCopy.getId());

		Collection<Parade> parades;
		parades = b.getParades();
		parades.add(saved);
		b.setParades(parades);

		return saved;
	}
}
