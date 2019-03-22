
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Service
@Transactional
public class HistoryService {

	@Autowired
	HistoryRepository		historyRepository;

	@Autowired
	ParadeService			paradeService;

	@Autowired
	InceptionRecordService	inceptionRecord;


	public History findOne(final int idHistory) {
		return this.historyRepository.findOne(idHistory);
	}

	public Collection<History> findAll() {
		return this.historyRepository.findAll();
	}

	public History createHistory() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		History h;
		h = new History();
		h.setInceptionRecord(new InceptionRecord());
		h.setLegalRecord(new ArrayList<LegalRecord>());
		h.setLinkRecord(new ArrayList<LinkRecord>());
		h.setMiscellaneousRecord(new ArrayList<MiscellaneousRecord>());
		h.setPeriodRecord(new ArrayList<PeriodRecord>());
		return h;
	}

	public History save(final History h) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		History saved;
		saved = this.historyRepository.save(h);

		return saved;
	}
}
