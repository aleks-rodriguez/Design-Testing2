
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	@Autowired(required = false)
	private Validator	validator;

	@Autowired
	FloatRepository		floatRepository;


	public Collection<Float> findAll() {
		return this.floatRepository.findAll();
	}

	public Float findOne(final int idFloat) {
		return this.floatRepository.findOne(idFloat);
	}

	public Brotherhood findBrotherhoodByUser(final int userId) {
		return this.floatRepository.findBrotherhoodByUserAccountId(userId);
	}

	public Brotherhood findBrotherhoodByFloat(final int idFloat) {
		return this.floatRepository.findBrotherhoodByFloatId(idFloat);
	}

	public Float createFloat() {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Float f;
		f = new Float();
		f.setTitle("");
		f.setDescription("");
		f.setPictures(new ArrayList<String>());
		return f;
	}

	public Float save(final Float f) {
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Float saved;
		saved = this.floatRepository.save(f);
		Brotherhood b;
		b = this.findBrotherhoodByUser(user.getId());
		Collection<Float> floatsPerBrotherhood;
		floatsPerBrotherhood = b.getFloats();
		if (f.getId() == 0) {
			floatsPerBrotherhood.add(saved);
			b.setFloats(floatsPerBrotherhood);
		}
		return saved;
	}

	public void delete(final int idFloat) {
		Assert.notNull(idFloat);
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Brotherhood b;
		b = this.floatRepository.findBrotherhoodByUserAccountId(user.getId());
		Float f;
		f = this.floatRepository.findOne(idFloat);
		for (final Procession procession : b.getProcessions()) {
			Collection<Float> floatPerProcession;
			floatPerProcession = procession.getFloats();
			if (floatPerProcession.contains(f)) {
				floatPerProcession.remove(f);
				procession.setFloats(floatPerProcession);
			}
		}
		Collection<Float> floatsPerBro;
		floatsPerBro = b.getFloats();
		floatsPerBro.remove(f);
		b.setFloats(floatsPerBro);
		this.floatRepository.delete(f);
	}

	public Float reconstruct(final Float f, final BindingResult binding) {
		Float result;
		if (f.getId() == 0)
			result = f;
		else {
			result = this.floatRepository.findOne(f.getId());
			result.setTitle(f.getTitle());
			result.setDescription(f.getDescription());
			result.setPictures(f.getPictures());
		}
		this.validator.validate(result, binding);
		return result;
	}
}
