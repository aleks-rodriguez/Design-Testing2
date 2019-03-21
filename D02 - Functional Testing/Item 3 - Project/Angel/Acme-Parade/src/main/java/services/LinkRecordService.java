
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LinkRecordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	@Autowired
	private Validator		validator;

	@Autowired
	LinkRecordRepository	linkRecordRepository;

	@Autowired
	ParadeService			paradeService;


	public LinkRecord findOne(final int idLink) {
		return this.linkRecordRepository.findOne(idLink);
	}

	public Collection<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}

	public LinkRecord createLinkRecord() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LinkRecord l;
		l = new LinkRecord();
		l.setTitle("");
		l.setDescription("");
		l.setBrotherhood(new Brotherhood());
		return l;
	}

	public LinkRecord save(final LinkRecord link) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LinkRecord saved;
		saved = this.linkRecordRepository.save(link);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<LinkRecord> linkRecordPerBrotherhood;
		linkRecordPerBrotherhood = h.getLinkRecord();
		if (link.getId() == 0) {
			linkRecordPerBrotherhood.add(link);
			h.setLinkRecord(linkRecordPerBrotherhood);
		}
		return saved;
	}

	public void delete(final int idlink) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		LinkRecord l;
		l = this.linkRecordRepository.findOne(idlink);
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		History h;
		h = b.getHistory();
		Collection<LinkRecord> linkRecordPerBrotherhood;
		linkRecordPerBrotherhood = h.getLinkRecord();
		linkRecordPerBrotherhood.remove(l);
		this.linkRecordRepository.delete(idlink);
	}

	public LinkRecord reconstruct(final LinkRecord l, final BindingResult binding) {
		LinkRecord result;
		if (l.getId() == 0)
			result = l;
		else {
			result = this.linkRecordRepository.findOne(l.getId());
			result.setTitle(l.getTitle());
			result.setDescription(l.getDescription());
			result.setBrotherhood(l.getBrotherhood());
		}
		this.validator.validate(result, binding);
		return result;
	}
}
