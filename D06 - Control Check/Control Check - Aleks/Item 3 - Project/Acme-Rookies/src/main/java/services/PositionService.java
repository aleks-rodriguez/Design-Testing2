
package services;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import repositories.TickerRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Company;
import domain.Position;
import domain.Problem;
import domain.Ticker;

@Service
@Transactional
public class PositionService extends AbstractService {

	@Autowired
	private PositionRepository	repository;

	@Autowired
	private TickerRepository	repositoryTicker;

	@Autowired
	private Validator			validator;

	private boolean				statusPrevious;


	public Company showCompanyByPosition(final int id) {
		return this.repository.showCompanyByPosition(id);
	}

	public Collection<Position> findPositionWithSponsorshipByProviderId(final int id) {
		return this.repository.findPositionWithSponsorshipByProviderId(id);
	}

	public Collection<Position> findPositionWithApplyByRookieId(final int id) {
		return this.repository.findPositionWithApplyByRookieId(id);
	}

	public Collection<Company> findAllCompanies() {
		return this.repository.findAllCompanies();
	}

	public Actor findByUserAccount(final int id) {
		return this.repository.findActorByUserAccountId(id);
	}
	public Collection<Problem> problemsByPosition(final int id) {
		return this.repository.problemsByPosition(id);
	}
	public Collection<Position> findAll() {
		return this.repository.findAll();
	}

	public Collection<Position> getPositionsByCompany(final int id) {
		return this.repository.getPositionsByCompany(id);
	}

	public Collection<Position> getPublicPositionsByCompany(final int id) {
		return this.repository.getPublicPositionsByCompany(id);
	}
	public Collection<Position> getPublicPositions() {
		return this.repository.getPublicPositions();
	}
	public Position findOne(final int id) {
		Position p;
		p = this.repository.findOne(id);
		this.statusPrevious = p.isFinalMode();
		return p;
	}
	public Ticker createTicker(final String commercialName) {
		Ticker ticker;
		ticker = new Ticker();
		ticker.setTicker(this.generateTicker(commercialName));
		return ticker;
	}
	public Position create() {

		super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY);

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Position position;
		position = new Position();
		//Tocar aqui

		position.setTicker(this.createTicker(c.getCommercialName()));
		position.setDeadline(new Date());
		position.setFinalMode(false);
		position.setDescription("");
		position.setSalary(0.0);
		position.setSkillsRequired("");
		position.setTechnologies("");
		position.setTitle("");
		position.setProfileRequired("");
		position.setCancel(false);

		return position;
	}
	public Position save(final Position arg, final boolean duplicate) {
		Position saved = null;

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Ticker ticker;

		if (duplicate == false)
			ticker = arg.getTicker();
		else
			ticker = this.createTicker(c.getCommercialName());

		if (arg.getId() == 0)
			arg.setCompany(c);
		else {
			Assert.isTrue(arg.getCompany().equals(c));
			Assert.isTrue(this.statusPrevious == false);
		}

		if (arg.isCancel() && !arg.getDescription().equals("loremIpsum"))
			Assert.isTrue(arg.isFinalMode() == true);

		if (arg.isFinalMode())
			Assert.isTrue(this.problemsByPosition(arg.getId()).size() >= 2);

		Assert.isTrue(arg.getDeadline().after(new Date()));

		Ticker saveT;
		saveT = this.repositoryTicker.saveAndFlush(ticker);

		arg.setTicker(saveT);

		saved = this.repository.saveAndFlush(arg);

		return saved;
	}

	public Position reconstruct(final Position aux, final BindingResult binding) {
		Position res;

		if (aux.getId() == 0)
			res = aux;
		else {
			res = this.repository.findOne(aux.getId());

			if (res.isFinalMode() == false) {
				res.setDeadline(aux.getDeadline());
				res.setDescription(aux.getDescription());
				res.setFinalMode(aux.isFinalMode());
				res.setProfileRequired(aux.getProfileRequired());
				res.setSalary(aux.getSalary());
				res.setSkillsRequired(aux.getSkillsRequired());
				res.setTitle(aux.getTitle());
				res.setTechnologies(aux.getTechnologies());
			} else
				throw new ValidationException();
		}

		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return res;
	}
	public void delete(final int id) {

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Position p;
		p = this.repository.findOne(id);

		Assert.isTrue(p.isFinalMode() == false);
		Assert.isTrue(p.getCompany().equals(c), "You don't have permission to do this");

		this.repository.delete(id);

	}

	public void cancel(final Position position) {

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Position aux;
		aux = this.repository.findOne(position.getId());

		Assert.isTrue(aux.isFinalMode() == true);
		Assert.isTrue(aux.getCompany().equals(c), "You don't have permission to do this");

		aux.setCancel(true);
	}

	public String generateTicker(String commercialName) {
		commercialName = super.limpiaCadena(commercialName).substring(0, 4);
		return commercialName + "-" + new SecureRandom().nextInt(10000);
	}

	public void flush() {
		this.repository.flush();
	}

}
