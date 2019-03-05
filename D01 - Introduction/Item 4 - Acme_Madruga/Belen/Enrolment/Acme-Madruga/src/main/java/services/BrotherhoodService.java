
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import utilities.Utiles;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Profile;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


	public Collection<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	public Brotherhood findOne(final int id) {
		return this.brotherhoodRepository.findOne(id);
	}

	public Actor findByUserAccount(final int id) {
		return this.brotherhoodRepository.getByUserAccount(id);
	}

	public Member findMemberByUser(final int id) {
		return this.brotherhoodRepository.getMemberByUserAccount(id);
	}

	public Collection<Member> getAllMemberByBrotherhood(final int idBrotherhood) {
		return this.brotherhoodRepository.getAllMemberByBrotherhood(idBrotherhood);
	}

	public Collection<Brotherhood> getAllBrotherhoodActiveByMemberId(final int idMember) {
		return this.brotherhoodRepository.getAllBrotherhoodActiveByMemberId(idMember);
	}

	public Collection<Brotherhood> getAllBrotherhoodDisByMemberId(final int idMember) {
		return this.brotherhoodRepository.getAllBrotherhoodDisByMemberId(idMember);
	}

	public Enrolment getEnrolmentByMemberAndBrother(final int idMember, final int idBrotherhood) {
		return this.brotherhoodRepository.getEnrolmentByMemberAndBrother(idMember, idBrotherhood);
	}

	public Brotherhood createBrotherhoodd() {

		Brotherhood brotherhood;
		brotherhood = new Brotherhood();

		//authority
		Authority auth;
		auth = new Authority();
		Collection<Authority> authorities;
		authorities = new ArrayList<>();
		auth.setAuthority(Authority.BROTHERHOOD);
		authorities.add(auth);

		//account
		UserAccount account;
		account = new UserAccount();
		account.setEnabled(true);
		account.setUsername("");
		account.setPassword("");
		account.setAuthorities(authorities);
		brotherhood.setAccount(account);
		brotherhood.setProfiles(new ArrayList<Profile>());

		//store:
		brotherhood.setName("");
		brotherhood.setMiddleName("");
		brotherhood.setSurname("");
		brotherhood.setPhoto("");
		brotherhood.setEmail("");
		brotherhood.setPhone("");
		brotherhood.setAdress("");

		brotherhood.setTitle("");
		brotherhood.setEstablishment(new Date());
		brotherhood.setPictures(new ArrayList<String>());

		brotherhood.setArea(new Area());
		brotherhood.setEnrolments(new ArrayList<Enrolment>());
		//brotherhood.setFloats(new ArrayList<Float>());

		return brotherhood;
	}

	public Brotherhood save(final Brotherhood brotherhood) {

		if (brotherhood.getId() != 0) {
			final UserAccount account = this.findByUserAccount(brotherhood.getId()).getAccount();
			account.setUsername(brotherhood.getAccount().getUsername());
			account.setPassword(Utiles.hashPassword(brotherhood.getAccount().getPassword()));
			account.setAuthorities(brotherhood.getAccount().getAuthorities());
			brotherhood.setAccount(account);
		} else
			brotherhood.getAccount().setPassword(Utiles.hashPassword(brotherhood.getAccount().getPassword()));
		//final Collection<Box> boxes = this.boxService.save(Utiles.initBoxes());
		//brotherhood.setBoxes(boxes);

		Brotherhood modify;

		modify = this.brotherhoodRepository.saveAndFlush(brotherhood);

		return modify;
	}

}
