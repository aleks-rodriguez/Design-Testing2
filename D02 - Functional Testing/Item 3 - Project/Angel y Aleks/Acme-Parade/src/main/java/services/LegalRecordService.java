
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LegalRecordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	@Autowired
	private Validator		validator;

	@Autowired
	LegalRecordRepository	legalRecordRepository;

	@Autowired
	ParadeService			paradeService;


	public LegalRecord findOne(final int idLegal) {
		return this.legalRecordRepository.findOne(idLegal);
	}

	public Collection<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}

	public LegalRecord createLegalRecord() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LegalRecord l;
		l = new LegalRecord();
		l.setTitle("");
		l.setDescription("");
		l.setVat(0);
		l.setLegalName("");
		l.setLaws(new ArrayList<String>());
		return l;
	}

	public LegalRecord save(final LegalRecord legal) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LegalRecord saved;
		saved = this.legalRecordRepository.save(legal);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Assert.notNull(b.getHistory(), "You don't have access");
		Collection<LegalRecord> legalRecordPerBrotherhood;
		legalRecordPerBrotherhood = h.getLegalRecord();
		if (legal.getId() == 0) {
			legalRecordPerBrotherhood.add(saved);
			h.setLegalRecord(legalRecordPerBrotherhood);
		} else
			Assert.isTrue(b.getHistory().getLegalRecord().contains(this.findOne(saved.getId())), "You don't have access to edit this legal record");
		return saved;
	}

	public void delete(final int idLegal) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LegalRecord l;
		l = this.legalRecordRepository.findOne(idLegal);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getLegalRecord().contains(this.findOne(l.getId())), "You don't have access to delete this legal record");
		Collection<LegalRecord> legalRecordPerBrotherhood;
		legalRecordPerBrotherhood = h.getLegalRecord();
		legalRecordPerBrotherhood.remove(l);
		this.legalRecordRepository.delete(idLegal);
	}

	public LegalRecord reconstruct(final LegalRecord l, final BindingResult binding) {
		LegalRecord result;
		if (l.getId() == 0)
			result = l;
		else {
			result = this.legalRecordRepository.findOne(l.getId());
			result.setTitle(l.getTitle());
			result.setDescription(l.getDescription());
			result.setLegalName(l.getLegalName());
			result.setVat(l.getVat());
			result.setLaws(l.getLaws());
		}
		this.validator.validate(result, binding);
		return result;
	}

}
