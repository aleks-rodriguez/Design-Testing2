
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InceptionRecordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	@Autowired
	private Validator			validator;

	@Autowired
	InceptionRecordRepository	inceptionRecordRepository;

	@Autowired
	ParadeService				paradeService;

	@Autowired
	HistoryService				historyService;


	public InceptionRecord findOne(final int idInception) {
		return this.inceptionRecordRepository.findOne(idInception);
	}

	public Collection<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

	public InceptionRecord findHistoryByInceptionRecordId(final int idInception) {
		return this.inceptionRecordRepository.findHistoryByInceptionRecordId(idInception);
	}

	public InceptionRecord createInceptionRecord() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		InceptionRecord i;
		i = new InceptionRecord();
		i.setTitle("");
		i.setDescription("");
		i.setPhotos(new ArrayList<String>());
		return i;
	}

	public InceptionRecord save(final InceptionRecord inception) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		InceptionRecord saved;
		saved = this.inceptionRecordRepository.save(inception);

		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());

		History h;
		h = b.getHistory();
		if (h == null) {
			h = this.historyService.createHistory();
			History savedHistory;
			savedHistory = this.historyService.save(h);
			b.setHistory(savedHistory);
		}
		b.getHistory().setInceptionRecord(saved);
		return saved;
	}

	public InceptionRecord reconstruct(final InceptionRecord i, final BindingResult binding) {
		InceptionRecord result;
		if (i.getId() == 0)
			result = i;
		else {
			result = this.inceptionRecordRepository.findOne(i.getId());
			result.setTitle(i.getTitle());
			result.setDescription(i.getDescription());
			result.setPhotos(i.getPhotos());
		}
		this.validator.validate(result, binding);
		return result;
	}
}
