
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

import repositories.EducationDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;

@Service
@Transactional
public class EducationDataService extends AbstractService {

	@Autowired
	private EducationDataRepository	repository;

	@Autowired
	private CurriculaService		serviceCurricula;

	@Autowired
	private Validator				validator;


	public Collection<EducationData> saveIterable(final Collection<EducationData> save) {
		return this.repository.save(save);
	}

	public Curricula findCurriculaByEducationDataId(final int id) {
		return this.repository.findCurriculaByEducationDataId(id);
	}

	public EducationData findOne(final int id) {
		return this.repository.findOne(id);
	}

	public EducationData create() {
		EducationData result;

		result = new EducationData();

		result.setInstitution("");
		result.setDegree("");
		result.setEndDate(new Date());
		result.setMark(0.0);
		result.setStartDate(new Date());

		return result;
	}
	public EducationData copyFactory(final EducationData copy) {
		EducationData result;

		result = new EducationData();

		result.setInstitution(copy.getInstitution());
		result.setDegree(copy.getDegree());
		result.setEndDate(copy.getEndDate());
		result.setMark(copy.getMark());
		result.setStartDate(copy.getStartDate());

		return result;
	}
	public EducationData save(final EducationData saveTo, final int curricula) {

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Curricula c;

		c = this.serviceCurricula.findOne(curricula);

		Assert.isTrue(h.equals(c.getHacker()));

		EducationData result;

		result = this.repository.save(saveTo);

		Collection<EducationData> col;
		col = c.getEducationData();

		if (!col.contains(result)) {
			col.add(result);
			c.setEducationData(col);
		}

		return result;

	}

	public EducationData reconstruct(final EducationData reconsTo, final BindingResult binding) {
		EducationData result;

		if (reconsTo.getId() == 0)
			result = reconsTo;
		else {
			result = this.repository.findOne(reconsTo.getId());
			result.setInstitution(reconsTo.getInstitution());
			result.setDegree(reconsTo.getDegree());
			result.setEndDate(reconsTo.getEndDate());
			result.setStartDate(reconsTo.getStartDate());
			result.setMark(reconsTo.getMark());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void delete(final int id) {
		EducationData deleteTo;
		deleteTo = this.repository.findOne(id);

		Curricula c;
		c = this.repository.findCurriculaByEducationDataId(id);

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(c.getHacker().equals(h));

		Collection<EducationData> col;
		col = c.getEducationData();

		col.remove(deleteTo);

		c.setEducationData(col);
	}

	public void deleteAllEducationData(final Curricula c) {
		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(c.getHacker().equals(h));
		this.repository.delete(c.getEducationData());
	}
}
