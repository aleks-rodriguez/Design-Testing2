
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
import domain.Rookie;

@Service
@Transactional
public class EducationDataService extends AbstractService {

	@Autowired
	private EducationDataRepository	repository;

	@Autowired
	private CurriculaService		serviceCurricula;

	@Autowired
	private Validator				validator;


	public void flush() {
		this.repository.flush();
	}

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

		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Curricula c;

		c = this.serviceCurricula.findOne(curricula);

		Assert.isTrue(h.equals(c.getRookie()));

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

		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(c.getRookie().equals(h));

		Collection<EducationData> col;
		col = c.getEducationData();

		col.remove(deleteTo);

		c.setEducationData(col);
	}

	public void deleteAllEducationData(final Curricula c) {
		Rookie h;
		h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(c.getRookie().equals(h), "You don't have permission to do this");
		this.repository.delete(c.getEducationData());
	}

	public boolean checkDates(final Date begin, final Date end) {
		boolean res;
		if (begin == null || end == null)
			res = false;
		else
			res = begin.before(end) && end.after(begin);
		return res;
	}
	public void delete(final Collection<EducationData> edu) {
		this.repository.delete(edu);//Este delete es el delete(Iterable) del repo
	}
}
