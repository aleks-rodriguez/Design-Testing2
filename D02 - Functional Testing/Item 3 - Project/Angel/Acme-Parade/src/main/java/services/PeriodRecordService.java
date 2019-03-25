
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PeriodRecordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	@Autowired
	private Validator		validator;

	@Autowired
	PeriodRecordRepository	periodRecordRepository;

	@Autowired
	ParadeService			paradeService;


	public PeriodRecord findOne(final int idPeriod) {
		return this.periodRecordRepository.findOne(idPeriod);
	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord createPeriodRecord() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		PeriodRecord p;
		p = new PeriodRecord();
		p.setTitle("");
		p.setDescription("");
		p.setStartDate(new Date());
		p.setEndDate(new Date());
		p.setPhotos(new ArrayList<String>());
		return p;
	}

	public PeriodRecord save(final PeriodRecord period) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		PeriodRecord saved;
		saved = this.periodRecordRepository.save(period);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<PeriodRecord> periodRecordPerBrotherhood;
		periodRecordPerBrotherhood = h.getPeriodRecord();
		if (period.getId() == 0) {
			periodRecordPerBrotherhood.add(saved);
			h.setPeriodRecord(periodRecordPerBrotherhood);
		}
		return saved;
	}

	public void delete(final int idPeriod) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		PeriodRecord l;
		l = this.periodRecordRepository.findOne(idPeriod);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<PeriodRecord> periodRecordPerBrotherhood;
		periodRecordPerBrotherhood = h.getPeriodRecord();
		periodRecordPerBrotherhood.remove(l);
		this.periodRecordRepository.delete(idPeriod);
	}

	public PeriodRecord reconstruct(final PeriodRecord p, final BindingResult binding) {
		PeriodRecord result;
		if (p.getId() == 0)
			result = p;
		else {
			result = this.periodRecordRepository.findOne(p.getId());
			result.setTitle(p.getTitle());
			result.setDescription(p.getDescription());
			result.setStartDate(p.getStartDate());
			result.setEndDate(p.getEndDate());
			result.setPhotos(p.getPhotos());
		}
		this.validator.validate(result, binding);
		return result;
	}

}
