
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

	@Autowired
	private Validator			validator;

	@Autowired
	private ParadeRepository	processionRepository;


	public Collection<Parade> findParadesByBrotherhoodIdFM(final int id) {
		return this.processionRepository.findParadesByBrotherhoodIdFM(id);
	}

	public Collection<Parade> findParadesByBrotherhoodId(final int id) {
		final String lang = LocaleContextHolder.getLocale().getLanguage();
		Collection<Parade> res = null;
		if (lang == "en" || lang == null) {
			res = this.processionRepository.findParadesByBrotherhoodId(id);
			if (res.isEmpty())
				res = this.processionRepository.findParadesByBrotherhoodIdES(id);
		} else {
			res = this.processionRepository.findParadesByBrotherhoodIdES(id);
			if (res.isEmpty())
				res = this.processionRepository.findParadesByBrotherhoodId(id);
		}
		return res;
	}

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
		return this.processionRepository.findParadesByBrotherhoodId(idBrotherhood);
	}

	public Collection<Parade> findParadesAFM() {
		return this.processionRepository.findParadesAFM();
	}

	public Parade createParade() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Parade p;
		p = new Parade();
		p.setTicker(Utiles.generateTicker(this.processionRepository.findAllTickersSystem()));
		p.setTitle("");
		p.setDescription("");
		p.setMomentOrganised(new Date());
		p.setFinalMode(false);
		p.setRequests(new ArrayList<Request>());
		p.setFloats(new ArrayList<Float>());
		p.setStatus("SUBMITTED");
		p.setWhyRejected("");
		return p;
	}
	public Parade save(final Parade parade) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Parade saved = null;
		if (Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD)) {
			saved = this.processionRepository.save(parade);
			Brotherhood b;
			b = this.processionRepository.findBrotherhoodByUserAccountId(user.getId());
			Collection<Parade> parades;
			parades = b.getParades();
			if (!parades.contains(saved)) {
				parades.add(saved);
				b.setParades(parades);
			}
		} else if (Utiles.findAuthority(user.getAuthorities(), Authority.CHAPTER))
			saved = this.processionRepository.save(parade);

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

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Parade copyParade(final int id) {

		Parade toCopy;
		toCopy = this.processionRepository.findOne(id);

		Brotherhood b;
		b = this.findBrotherhoodByParade(toCopy.getId());

		Assert.isTrue(b.getAccount().getId() == LoginService.getPrincipal().getId());
		Assert.isTrue(toCopy.getFinalMode());

		Parade newParade;
		newParade = this.createParade();

		newParade.setTitle(toCopy.getTitle());
		newParade.setDescription(toCopy.getDescription());
		newParade.setFinalMode(false);
		newParade.setFloats(toCopy.getFloats());
		newParade.setMomentOrganised(toCopy.getMomentOrganised());
		newParade.setRequests(toCopy.getRequests());
		newParade.setTicker(Utiles.generateTicker(this.processionRepository.findAllTickersSystem()));
		newParade.setStatus("SUBMITTED");
		newParade.setWhyRejected("");

		Parade saved;
		saved = this.processionRepository.save(newParade);

		Collection<Parade> parades;
		parades = b.getParades();
		parades.add(saved);
		b.setParades(parades);

		return saved;
	}
}
