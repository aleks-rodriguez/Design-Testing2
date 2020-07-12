
package services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PelfRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Audit;
import domain.Company;
import domain.Pelf;
import domain.Ticker;

@Service
@Transactional
public class PelfService extends AbstractService {

	@Autowired
	private PelfRepository	pelfRepo;
	@Autowired
	private AuditService	auditService;
	@Autowired
	private Validator		validator;


	public Pelf findOne(final int id) {
		return this.pelfRepo.findOne(id);
	}
	public Collection<Pelf> findAllFinalByAuditId(final int id) {
		return this.pelfRepo.findAllFinalByAuditId(id);
	}

	public Collection<Pelf> findAllByCompany(final int companyId, final int auditId) {
		return this.pelfRepo.findAllByCompany(companyId, auditId);
	}

	public Actor findActorByUserAccountId(final int accountId) {
		return this.pelfRepo.findActorByUserAccountId(accountId);
	}

	public Collection<Pelf> findAllByCompanyId(final int id) {
		return this.pelfRepo.findAllByCompanyId(id);
	}

	public Pelf create(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
		Assert.isTrue(LoginService.getPrincipal().getId() == this.auditService.findOne(id).getPosition().getCompany().getAccount().getId());
		Pelf q;
		q = new Pelf();
		Audit au;
		au = this.auditService.findOne(id);
		q.setAudit(au);
		q.setBody("");
		q.setCompany((Company) this.pelfRepo.findActorByUserAccountId(LoginService.getPrincipal().getId()));
		q.setFinalMode(false);
		q.setPicture("");
		q.setPublicationMoment(null);
		q.setTicker(null);

		return q;
	}

	public Pelf save(final Pelf q) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
		Assert.isTrue(LoginService.getPrincipal().getId() == q.getAudit().getPosition().getCompany().getAccount().getId());
		Pelf saved = null;
		Company c;
		c = (Company) this.pelfRepo.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Collection<Pelf> ownPelfs;
		ownPelfs = new ArrayList<Pelf>();
		ownPelfs = this.pelfRepo.findAllByCompanyId(c.getId());

		if (this.pelfRepo.findAllTickers().contains(q.getTicker().getTicker()) && q.getId() == 0) {
			Ticker t;
			t = this.createTicker();
			q.setTicker(t);
		}

		if (q.getId() == 0)
			saved = this.pelfRepo.save(q);
		else {
			Assert.isTrue(ownPelfs.contains(q));
			saved = this.pelfRepo.save(q);
		}

		return saved;
	}

	public void delete(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));

		Company c;
		c = (Company) this.pelfRepo.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Pelf q;
		q = this.pelfRepo.findOne(id);
		Assert.isTrue(!q.isFinalMode());
		Assert.isTrue(q.getCompany().equals(c));
		Assert.isTrue(LoginService.getPrincipal().getId() == q.getAudit().getPosition().getCompany().getAccount().getId());

		this.pelfRepo.delete(q);
	}

	public void delete(final Collection<Pelf> col) {
		this.pelfRepo.delete(col);
	}

	public Pelf reconstruct(final Pelf pelf, final BindingResult binding) {
		Pelf result;
		if (pelf.getId() == 0) {
			result = pelf;
			result.setCompany((Company) this.pelfRepo.findActorByUserAccountId(LoginService.getPrincipal().getId()));
			result.setPublicationMoment(new Date());
			result.setTicker(this.createTicker());

		} else {
			result = this.pelfRepo.findOne(pelf.getId());
			Assert.isTrue(!result.isFinalMode());
			result.setBody(pelf.getBody());
			result.setFinalMode(pelf.isFinalMode());
			result.setPicture(pelf.getPicture());
			result.setPublicationMoment(new Date());
		}
		this.validator.validate(result, binding);
		if (binding.hasErrors()) {
			result.setFinalMode(false);
			throw new ValidationException();
		}
		return result;
	}

	public Ticker createTicker() {
		Ticker ticker;
		ticker = new Ticker();
		ticker.setTicker(this.generateTickerCC());
		return ticker;
	}

	private String generateTickerCC() {
		String s;
		do {

			SimpleDateFormat formato;
			//Aquí habría que cambiar el pattern que te digan
			formato = new SimpleDateFormat("yyMMdd");

			String formated;

			formated = formato.format(new Date());

			final Character[] ch = {
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
			};

			String c = "";

			final SecureRandom random;

			random = new SecureRandom();

			for (int i = 0; i < 6; i++)
				c += ch[random.nextInt(ch.length)];

			s = formated.substring(0, 2) + "" + formated.substring(2, 4) + "" + formated.substring(4, 6) + ":" + c;
		} while (this.pelfRepo.findAllTickers().contains(s));

		return s;
	}

	public void flush() {
		this.pelfRepo.flush();
	}
}
