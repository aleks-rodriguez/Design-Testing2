
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditService extends AbstractService {

	@Autowired
	private AuditRepository	repository;

	@Autowired
	private PositionService	servicePos;

	@Autowired
	private Validator		validator;

	private boolean			auxFinalMode;


	public boolean checkAudit(final int audit) {
		Auditor auditor;
		auditor = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		return this.repository.findOne(audit).getAuditor().getId() == auditor.getId();
	}

	public Actor findActorByUserAccount(final int id) {
		return this.repository.findActorByUserAccountId(id);
	}

	public Collection<Position> findPositionsByAuditor(final int id) {
		return this.repository.findPositionByAuditor(id);
	}

	public Collection<Audit> findAuditsByPositionPublic(final int id) {
		return this.repository.findAuditsByPositionPublic(id);
	}

	public Collection<Audit> findAuditsByPosition(final int id) {
		return this.repository.findAuditsByPosition(id);
	}

	public Collection<Double> findAllScoresByCompanyId(final int idCompany) {
		return this.repository.findAllScoresByCompanyId(idCompany);
	}

	public Collection<Audit> findAllAuditsByAuditor(final int auditorId) {
		return this.repository.findAuditsByAuditor(auditorId);
	}

	public Audit findOne(final int id) {
		Audit audit;
		audit = this.repository.findOne(id);

		this.auxFinalMode = audit.isFinalMode();

		return audit;
	}

	public Audit create(final int position) {

		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR), "You are not authorized");
		Audit audit;
		audit = new Audit();

		audit.setFinalMode(false);
		audit.setMoment(new Date());
		audit.setScore(0.0);
		audit.setText("");
		audit.setPosition(this.servicePos.findOne(position));

		return audit;
	}
	public Audit save(final Audit audit) {
		Audit saved;

		Auditor auditor;
		auditor = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(super.findAuthority(auditor.getAccount().getAuthorities(), Authority.AUDITOR), "You are not authorized");
		Assert.isTrue(this.auxFinalMode == false);

		if (audit.getId() == 0)
			audit.setAuditor(auditor);
		else
			Assert.isTrue(this.repository.findAuditsByAuditor(auditor.getId()).contains(audit), "You can not edit this audit");

		saved = this.repository.save(audit);

		return saved;
	}

	public void save(final Collection<Audit> col) {
		this.repository.save(col);
	}
	public void delete(final int id) {
		Auditor auditor;
		auditor = (Auditor) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Audit audit;
		audit = this.repository.findOne(id);

		Assert.isTrue(super.findAuthority(auditor.getAccount().getAuthorities(), Authority.AUDITOR), "You are not authorized");
		Assert.isTrue(audit.getAuditor().getId() == auditor.getId(), "You can not edit this audit");

		if (audit.isFinalMode())
			throw new IllegalArgumentException();
		else
			this.repository.delete(audit);
	}
	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0)
			result = audit;
		else {
			result = this.repository.findOne(audit.getId());
			if (result.isFinalMode() == false) {
				result.setMoment(audit.getMoment());
				result.setFinalMode(audit.isFinalMode());
				result.setScore(audit.getScore());
				result.setText(audit.getText());
				result.setPosition(audit.getPosition());
			}
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors()) {
			result.setFinalMode(false);
			throw new ValidationException();
		}
		return result;

	}
}
