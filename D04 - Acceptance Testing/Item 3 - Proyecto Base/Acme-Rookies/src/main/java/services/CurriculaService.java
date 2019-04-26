
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculaRepository;
import security.Authority;
import security.LoginService;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PositionData;
import domain.Rookie;

@Service
@Transactional
public class CurriculaService extends AbstractService {

	@Autowired
	private CurriculaRepository			repository;

	@Autowired
	private Validator					validator;

	@Autowired
	private PositionDataService			servicePData;

	@Autowired
	private EducationDataService		educationData;

	@Autowired
	private MiscellaneousDataService	miscData;


	public Curricula create() {

		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Curricula curricula;
		curricula = new Curricula();

		curricula.setFullName(h.getName() + " " + h.getSurname());
		curricula.setPhoneNumber(h.getPhone());

		curricula.setPositionsData(new ArrayList<PositionData>());
		curricula.setEducationData(new ArrayList<EducationData>());
		curricula.setMiscellaneousData(new ArrayList<MiscellaneousData>());

		curricula.setGithubProfile("");
		curricula.setLinkedInProfile("");
		curricula.setStatement("");
		curricula.setRookie(h);

		return curricula;
	}

	public Collection<Curricula> findAllByRookie() {
		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		try {
			return this.repository.findAllByRookie(h.getId());
		} catch (final NullPointerException oops) {
			return new ArrayList<Curricula>();
		}

	}

	public Curricula findOne(final int id) {
		return this.repository.findOne(id);
	}

	public Curricula save(final Curricula save) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));

		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		if (save.getId() == 0)
			save.setRookie(h);
		else
			Assert.isTrue(save.getRookie().equals(h));

		super.checkLinkedInOrGithub(save.getGithubProfile());
		super.checkLinkedInOrGithub(save.getLinkedInProfile());
		Curricula result;

		result = this.repository.save(save);

		return result;
	}

	public void delete(final int id) {

		Curricula c;
		c = this.repository.findOne(id);
		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(c.getRookie().equals(h));

		//Delete all - EducationData
		this.educationData.deleteAllEducationData(c);
		//Delete all - PositionData
		this.servicePData.deleteAllPositionData(c);
		//Delete all - Miscellaneous Data
		this.miscData.deleteAllMiscellaneousData(c);
		//Delete Curricula
		this.repository.delete(c);
	}

	public Curricula reconstruct(final Curricula curricula, final BindingResult binding) {
		Curricula result;
		if (curricula.getId() == 0)
			result = curricula;
		else {
			result = this.repository.findOne(curricula.getId());
			result.setPhoneNumber(curricula.getPhoneNumber());
			result.setLinkedInProfile(curricula.getLinkedInProfile());
			result.setGithubProfile(curricula.getGithubProfile());
			result.setStatement(curricula.getStatement());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Curricula copyCurricula(final int id) {

		Curricula from;
		from = this.repository.findOne(id);

		Curricula to;
		to = this.create();

		to.setFullName(from.getFullName());
		to.setGithubProfile(from.getGithubProfile());
		to.setLinkedInProfile(from.getLinkedInProfile());
		to.setPhoneNumber(from.getPhoneNumber());
		to.setStatement(from.getStatement());

		Collection<EducationData> toEdu;
		Collection<PositionData> toPos;
		Collection<MiscellaneousData> toMisc;

		toEdu = new ArrayList<EducationData>();
		toPos = new ArrayList<PositionData>();
		toMisc = new ArrayList<MiscellaneousData>();

		for (final EducationData edu : from.getEducationData())
			toEdu.add(this.educationData.copyFactory(edu));

		for (final PositionData pos : from.getPositionsData())
			toPos.add(this.servicePData.copyFactory(pos));

		for (final MiscellaneousData misc : from.getMiscellaneousData())
			toMisc.add(this.miscData.copyFactory(misc));

		to.setCopy(true);

		Curricula save;
		save = this.repository.save(to);

		save.setEducationData(this.educationData.saveIterable(toEdu));
		save.setPositionsData(this.servicePData.saveIterable(toPos));
		save.setMiscellaneousData(this.miscData.saveIterable(toMisc));

		return save;

	}

	public void flush() {
		this.repository.flush();
	}
}
