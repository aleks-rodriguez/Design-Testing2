
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousRecordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private Validator				validator;

	@Autowired
	MiscellaneousRecordRepository	miscellaneousRecordRepository;

	@Autowired
	ParadeService					paradeService;


	public MiscellaneousRecord findOne(final int idMiscellaneous) {
		return this.miscellaneousRecordRepository.findOne(idMiscellaneous);
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord createMiscellaneousRecord() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		MiscellaneousRecord m;
		m = new MiscellaneousRecord();
		m.setTitle("");
		m.setDescription("");
		return m;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneous) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		MiscellaneousRecord saved;
		saved = this.miscellaneousRecordRepository.save(miscellaneous);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<MiscellaneousRecord> miscellaneousRecordPerBrotherhood;
		miscellaneousRecordPerBrotherhood = h.getMiscellaneousRecord();
		if (miscellaneous.getId() == 0) {
			miscellaneousRecordPerBrotherhood.add(miscellaneous);
			h.setMiscellaneousRecord(miscellaneousRecordPerBrotherhood);
		}
		return saved;
	}

	public void delete(final int idMiscellaneous) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		MiscellaneousRecord l;
		l = this.miscellaneousRecordRepository.findOne(idMiscellaneous);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<MiscellaneousRecord> miscellaneousRecordPerBrotherhood;
		miscellaneousRecordPerBrotherhood = h.getMiscellaneousRecord();
		miscellaneousRecordPerBrotherhood.remove(l);
		this.miscellaneousRecordRepository.delete(idMiscellaneous);
	}

	public MiscellaneousRecord reconstruct(final MiscellaneousRecord m, final BindingResult binding) {
		MiscellaneousRecord result;
		if (m.getId() == 0)
			result = m;
		else {
			result = this.miscellaneousRecordRepository.findOne(m.getId());
			result.setTitle(m.getTitle());
			result.setDescription(m.getDescription());
		}
		this.validator.validate(result, binding);
		return result;
	}

}
