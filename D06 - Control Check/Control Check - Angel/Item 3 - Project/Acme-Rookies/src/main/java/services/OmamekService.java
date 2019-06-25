
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OmamekRepository;
import security.Authority;
import security.LoginService;
import ticketable.TickerServiceInter;
import domain.Actor;
import domain.Audit;
import domain.Company;
import domain.Omamek;

@Service
@Transactional
public class OmamekService extends AbstractService {

	@Autowired
	private OmamekRepository	repository;

	@Autowired
	private TickerServiceInter	interm;

	@Autowired
	private Validator			validator;

	@Autowired
	private AuditService		serviceAudit;

	private boolean				finalMode;


	public Actor findActorByUA() {
		return this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
	}

	public Collection<Omamek> findOmameksByAuditFM(final int audit) {
		return this.repository.findOmameksByAuditFM(audit);
	}

	public Collection<Omamek> findOmameksByAuditDraftMode(final int audit) {

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = this.serviceAudit.findOne(audit);

		Assert.isTrue(c.getId() == au.getPosition().getCompany().getId());

		return this.repository.findOmameksByAuditDraftMode(audit, c.getId());
	}

	public Omamek findOne(final int id) {

		Omamek omamek;

		omamek = this.repository.findOne(id);

		omamek.setTicker(this.repository.findTickerOmamek(id));

		this.finalMode = omamek.isFinalMode();

		return omamek;
	}

	public Omamek create(final int audit) {

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = this.serviceAudit.findOne(audit);

		Assert.isTrue(c.getId() == au.getPosition().getCompany().getId());
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));

		Omamek omamek;
		omamek = new Omamek();

		omamek.setDescription("");
		omamek.setImage("");
		omamek.setMoment(new Date());
		omamek.setTitle("");
		omamek.setAudit(au);
		omamek.setTicker(this.interm.create("\\d{6}", ":", new SimpleDateFormat("yyyy:MM:dd").format(omamek.getMoment())));
		omamek.setFinalMode(false);

		return omamek;
	}

	public Omamek save(final Omamek without) {

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = without.getAudit();

		Assert.isTrue(c.getId() == au.getPosition().getCompany().getId());
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));

		Omamek saved;

		saved = this.interm.withTicker(without, this.repository, new SimpleDateFormat("yyyy:MM:dd").format(without.getMoment()), "\\d{6}", false);

		return saved;
	}

	public void delete(final int id) {
		Omamek omamek;
		omamek = this.repository.findOne(id);

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(omamek.isFinalMode() == false);
		Assert.isTrue(c.getId() == omamek.getAudit().getPosition().getCompany().getId());

		this.repository.delete(id);
	}
	public Omamek reconstruct(final Omamek reconstTo, final BindingResult binding) {
		Omamek result;
		if (reconstTo.getId() == 0)
			result = reconstTo;
		else {
			result = this.repository.findOne(reconstTo.getId());
			if (this.finalMode == false) {
				result.setDescription(reconstTo.getDescription());
				result.setImage(reconstTo.getImage());
				result.setMoment(reconstTo.getMoment());
				result.setTitle(reconstTo.getTitle());
				result.setFinalMode(reconstTo.isFinalMode());
			} else
				throw new IllegalArgumentException();
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	public void flush() {
		this.repository.flush();
	}
}
