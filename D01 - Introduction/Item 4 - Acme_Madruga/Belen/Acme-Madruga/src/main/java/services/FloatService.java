
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Float;

@Service
@Transactional
public class FloatService {

	@Autowired
	FloatRepository	floatRepository;


	public Collection<Float> findAll() {
		return this.floatRepository.findAll();
	}

	public Float findOne(final int idFloat) {
		return this.floatRepository.findOne(idFloat);
	}

	public Brotherhood findBrotherhoodByUser(final int userId) {
		return this.floatRepository.findBrotherhoodByUserAccountId(userId);
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
		return saved;
	}

	public void delete(final int idFloat) {
		Assert.notNull(idFloat);
		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		Assert.isTrue(Utiles.findAuthority(user.getAuthorities(), Authority.BROTHERHOOD));
		Float f;
		f = this.floatRepository.findOne(idFloat);
		this.floatRepository.delete(f);
	}
}
