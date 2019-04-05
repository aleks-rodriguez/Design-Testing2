
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Company;
import domain.Position;
import domain.Ticker;

@Service
@Transactional
public class PositionService extends AbstractService {

	@Autowired
	private PositionRepository	repository;

	@Autowired
	private Validator			validator;


	public Actor findByUserAccount(final int id) {
		return this.repository.findActorByUserAccountId(id);
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

	public Position findOne(final int id) {
		return this.repository.findOne(id);
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
		position.setTicker(this.generateTicker(c.getCommercialName()));
		position.setDeadline(new Date());
		position.setFinalMode(false);
		position.setDescription("");
		position.setSalary(0.0);
		position.setSkillsRequired("");
		position.setTechnologies("");
		position.setTitle("");
		position.setProfileRequired("");

		return position;
	}

	public Position save(final Position arg) {
		Position saved;

		Company c;
		c = (Company) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		if (arg.getId() == 0)
			arg.setCompany(c);
		else
			Assert.isTrue(arg.getCompany().equals(c));

		saved = this.repository.save(arg);
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
		Assert.isTrue(p.getCompany().equals(c));

		this.repository.delete(id);
	}
	public String generateTicker(String commercialName) {
		commercialName = super.limpiaCadena(commercialName).substring(0, 4);
		return commercialName + "-" + new Random().nextInt(10000);
	}
}
