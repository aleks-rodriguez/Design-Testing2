
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

import repositories.AoletRepository;
import security.Authority;
import security.LoginService;
import ticketable.TickerServiceInter;
import domain.Actor;
import domain.Aolet;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AoletService extends AbstractService {

	@Autowired
	private AoletRepository		repository;

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

	public Collection<Aolet> findAoletsByAuditFM(final int audit) {
		return this.repository.findAoletsByAuditFM(audit);
	}

	public Collection<Aolet> findAoletsByAuditDraftMode(final int audit) {

		Auditor c;
		c = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = this.serviceAudit.findOne(audit);

		Assert.isTrue(c.getId() == au.getAuditor().getId());

		return this.repository.findAoletsByAuditDraftMode(audit, c.getId());
	}

	public Aolet findOne(final int id) {

		Aolet aolet;

		aolet = this.repository.findOne(id);

		this.finalMode = aolet.isFinalMode();

		return aolet;
	}

	public Aolet create(final int audit) {

		Auditor c;
		c = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = this.serviceAudit.findOne(audit);

		Assert.isTrue(c.getId() == au.getAuditor().getId());
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));

		Aolet aolet;
		aolet = new Aolet();

		aolet.setDescription("");
		aolet.setImage("");
		aolet.setMoment(new Date());
		aolet.setTitle("");
		aolet.setAudit(au);
		aolet.setTicker(this.interm.create(new SimpleDateFormat("yyyy/MM/dd").format(aolet.getMoment()), "[0-9]{4}"));
		aolet.setFinalMode(false);

		return aolet;
	}

	public Aolet save(final Aolet without) {

		Auditor c;
		c = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Audit au;
		au = without.getAudit();

		Assert.isTrue(c.getId() == au.getAuditor().getId());
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));

		Aolet saved;

		saved = this.interm.withTicker(without, this.repository, new SimpleDateFormat("yyyy/MM/dd").format(without.getMoment()), "[0-9]{4}");

		return saved;
	}

	public void delete(final int id) {
		Aolet aolet;
		aolet = this.repository.findOne(id);

		Assert.isTrue(aolet.isFinalMode() == false);

		this.repository.delete(id);
	}
	public Aolet reconstruct(final Aolet reconstTo, final BindingResult binding) {
		Aolet result;
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
