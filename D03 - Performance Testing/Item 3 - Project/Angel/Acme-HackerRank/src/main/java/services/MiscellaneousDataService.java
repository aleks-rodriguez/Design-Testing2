
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

import repositories.MiscellaneousDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService extends AbstractService {

	@Autowired
	private MiscellaneousDataRepository	repository;

	@Autowired
	private CurriculaService			serviceCurricula;

	@Autowired
	private Validator					validator;


	public Collection<MiscellaneousData> saveIterable(final Collection<MiscellaneousData> save) {
		return this.repository.save(save);
	}

	public Curricula findCurriculaByMiscellaneousDataId(final int id) {
		return this.repository.findCurriculaByMiscellaneousDataId(id);
	}

	public MiscellaneousData findOne(final int id) {
		return this.repository.findOne(id);
	}

	public MiscellaneousData create() {
		MiscellaneousData result;

		result = new MiscellaneousData();

		result.setText("");
		result.setUrls(new ArrayList<String>());

		return result;
	}
	public MiscellaneousData copyFactory(final MiscellaneousData copy) {
		MiscellaneousData result;

		result = new MiscellaneousData();

		result.setText(copy.getText());
		result.setUrls(copy.getUrls());

		return result;
	}

	public MiscellaneousData save(final MiscellaneousData saveTo, final int curricula) {

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Curricula c;

		c = this.serviceCurricula.findOne(curricula);

		Assert.isTrue(h.equals(c.getHacker()));

		MiscellaneousData result;

		result = this.repository.save(saveTo);

		Collection<MiscellaneousData> col;
		col = c.getMiscellaneousData();

		if (!col.contains(result)) {
			col.add(result);
			c.setMiscellaneousData(col);
		}

		return result;

	}

	public MiscellaneousData reconstruct(final MiscellaneousData reconsTo, final BindingResult binding) {
		MiscellaneousData result;

		if (reconsTo.getId() == 0)
			result = reconsTo;
		else {
			result = this.repository.findOne(reconsTo.getId());
			result.setText(reconsTo.getText());
			result.setUrls(reconsTo.getUrls());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void delete(final int id) {
		MiscellaneousData deleteTo;
		deleteTo = this.repository.findOne(id);

		Curricula c;
		c = this.repository.findCurriculaByMiscellaneousDataId(id);

		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Assert.isTrue(c.getHacker().equals(h));

		Collection<MiscellaneousData> col;
		col = c.getMiscellaneousData();

		col.remove(deleteTo);

		c.setMiscellaneousData(col);
	}

	public void deleteAllMiscellaneousData(final Curricula c) {
		Hacker h;
		h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(c.getHacker().equals(h));
		this.repository.delete(c.getMiscellaneousData());
	}
}
