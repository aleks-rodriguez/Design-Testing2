
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private Validator			validator;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private EnrolmentRepository	enrolmentrepository;


	public Enrolment findOne(final int idEnrolment) {
		return this.enrolmentrepository.findOne(idEnrolment);
	}

	public Collection<Enrolment> findAll() {
		return this.enrolmentrepository.findAll();
	}

	public Collection<Enrolment> findEnrolmentsByMemberId(final int memberId) {
		return this.enrolmentrepository.getEnrolmentsByAMember(memberId);
	}

	public Collection<Enrolment> findEnrolmentWithoutPosition(final int idBrotherhood) {
		return this.enrolmentrepository.getEnrolmentWithoutPosition(idBrotherhood);
	}

	public Enrolment createEnrolment(final int idBrotherhood) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Member m;
		m = this.enrolmentrepository.findMemberByUserAccountId(user.getId());
		Enrolment e;
		e = new Enrolment();
		e.setMoment(new Date());
		e.setExitMoment(null);
		e.setExitMember(false);
		e.setBrotherhood(this.brotherhoodService.findOne(idBrotherhood));
		e.setMember(m);
		return e;
	}

	public Enrolment save(final Enrolment enrolment) {
		Enrolment saved;
		saved = this.enrolmentrepository.save(enrolment);
		Brotherhood b;
		b = enrolment.getBrotherhood();
		Collection<Enrolment> enrolPerBrotherhood;
		enrolPerBrotherhood = b.getEnrolments();
		enrolPerBrotherhood.add(saved);
		b.setEnrolments(enrolPerBrotherhood);
		return saved;
	}

	public void delete(final int idEnrolment) {
		Enrolment enrolment;
		enrolment = this.findOne(idEnrolment);
		enrolment.setExitMember(true);
		enrolment.setExitMoment(new Date());
	}

	public void readmite(final int idEnrolment) {
		Enrolment enrolment;
		enrolment = this.findOne(idEnrolment);
		enrolment.setExitMember(false);
		enrolment.setExitMoment(null);
	}

	public Enrolment reconstruct(final Enrolment enrolment, final BindingResult binding) {
		Enrolment result;
		if (enrolment.getId() == 0)
			result = enrolment;
		else {
			result = this.enrolmentrepository.findOne(enrolment.getId());
			result.setPosition(enrolment.getPosition());
			result.setId(enrolment.getId());
		}
		this.validator.validate(result, binding);
		return result;
	}
}