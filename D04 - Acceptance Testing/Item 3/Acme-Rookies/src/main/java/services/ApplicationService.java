
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

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Position;
import domain.Rookie;

@Service
@Transactional
public class ApplicationService extends AbstractService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CurriculaService		serviceCurricula;


	public Actor findActorByUserAccountId(final int idUser) {
		return this.applicationRepository.findActorByUserAccountId(idUser);
	}

	public Collection<Application> getApplicationByRookieId(final int idRookie) {
		return this.applicationRepository.getApplicationByRookieId(idRookie);
	}

	public Application findOne(final int idApplication) {
		return this.applicationRepository.findOne(idApplication);
	}

	public Collection<Application> getApplicationByPositionIdAndRookie(final int idRookie, final int idPosition) {
		return this.applicationRepository.getApplicationByPositionIdAndRookie(idRookie, idPosition);
	}

	public Collection<Application> findApplicationsByPositionId(final int i) {
		return this.applicationRepository.findApplicationsByPositionId(i);
	}

	public Application createApplication(final int idPosition) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		Rookie h;
		h = (Rookie) this.applicationRepository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(this.applicationRepository.getApplicationByPositionIdAndRookie(h.getId(), idPosition).size() < 1, "You don't have permission to do this because you've already created an application before");
		Application a;
		a = new Application();
		a.setApplicationMoment(new Date());
		a.setStatus("PENDING");
		a.setPosition(this.positionService.findOne(idPosition));
		a.setProblem(super.lookingForProblem(this.problemService.getProblemByPositionId(idPosition)));
		a.setRookie((Rookie) this.applicationRepository.findActorByUserAccountId(user.getId()));
		return a;
	}

	public Application save(final Application a) {

		UserAccount user;
		user = LoginService.getPrincipal();

		Collection<Application> col;

		Application saved = null;

		if (super.findAuthority(user.getAuthorities(), Authority.ROOKIE)) {
			Rookie h;
			h = (Rookie) this.applicationRepository.findActorByUserAccountId(user.getId());
			col = this.applicationRepository.getApplicationByRookieId(h.getId());
			if (a.getId() != 0) {
				Assert.isTrue(col.contains(a), "You don't have permission to do this");
				saved = this.applicationRepository.save(a);
			} else {
				Curricula c;
				c = a.getCurricula();
				a.setCurricula(this.serviceCurricula.copyCurricula(c.getId()));
				saved = this.applicationRepository.save(a);
			}
		} else if (super.findAuthority(user.getAuthorities(), Authority.COMPANY)) {
			Company c;
			c = (Company) this.applicationRepository.findActorByUserAccountId(user.getId());
			final Collection<Position> colPos = this.positionService.getPositionsByCompany(c.getId());
			Assert.isTrue(colPos.contains(a.getPosition()), "You don't have permission to do this");
			saved = this.applicationRepository.save(a);
		}

		return saved;
	}

	public Application reconstruct(final Application a, final BindingResult binding) {
		Application result;
		if (a.getId() == 0) {
			result = a;
			UserAccount user;
			user = LoginService.getPrincipal();
			result.setRookie((Rookie) this.applicationRepository.findActorByUserAccountId(user.getId()));
		} else {
			result = this.applicationRepository.findOne(a.getId());
			result.setApplicationMoment(a.getApplicationMoment());
			result.setPosition(a.getPosition());
			result.setProblem(a.getProblem());
			if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
				if (a.getStatus().equals("0"))
					binding.rejectValue("status", "application.wrong.status");
				result.setMoment(new Date());
				result.setStatus(a.getStatus());

			}
		}
		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}
}
