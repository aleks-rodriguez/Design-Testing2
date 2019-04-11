
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

import repositories.PositionDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService extends AbstractService {

	@Autowired
	private PositionDataRepository	repository;

	@Autowired
	private CurriculaService		serviceCurricula;

	@Autowired
	private Validator				validator;


	public Collection<PositionData> saveIterable(final Collection<PositionData> save) {
		return this.repository.save(save);
	}

	public Curricula findCurriculaByPositionDataId(final int id) {
		return this.repository.findCurriculaByPositionDataId(id);
	}

	public PositionData findOne(final int id) {
		return this.repository.findOne(id);
	}

	public PositionData create() {
		PositionData result;

		result = new PositionData();

		result.setStartDate(new Date());
		result.setEndDate(new Date());
		result.setTitle("");
		result.setDescription("");

		return result;
	}
	public PositionData copyFactory(final PositionData copy) {
		PositionData result;

		result = new PositionData();

		result.setStartDate(copy.getStartDate());
		result.setEndDate(copy.getEndDate());
		result.setTitle(copy.getTitle());
		result.setDescription(copy.getDescription());

		return result;
	}

	public PositionData save(final PositionData saveTo, final int curricula) {

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Curricula c;

		c = this.serviceCurricula.findOne(curricula);

		Assert.isTrue(h.equals(c.getHacker()));

		PositionData result;

		result = this.repository.save(saveTo);

		Collection<PositionData> col;
		col = c.getPositionsData();

		if (!col.contains(result)) {
			col.add(result);
			c.setPositionsData(col);
		}

		return result;

	}

	public PositionData reconstruct(final PositionData reconsTo, final BindingResult binding) {
		PositionData result;

		if (reconsTo.getId() == 0)
			result = reconsTo;
		else {
			result = this.repository.findOne(reconsTo.getId());
			result.setStartDate(reconsTo.getStartDate());
			result.setEndDate(reconsTo.getEndDate());
			result.setTitle(reconsTo.getTitle());
			result.setDescription(reconsTo.getDescription());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void delete(final int id) {
		PositionData deleteTo;
		deleteTo = this.repository.findOne(id);

		Curricula c;
		c = this.repository.findCurriculaByPositionDataId(id);

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(c.getHacker().equals(h));

		Collection<PositionData> col;
		col = c.getPositionsData();

		col.remove(deleteTo);

		c.setPositionsData(col);
	}

	public void deleteAllPositionData(final Curricula c) {
		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(c.getHacker().equals(h));
		this.repository.delete(c.getPositionsData());
	}
}
