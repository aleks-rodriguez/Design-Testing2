
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import utilities.Utiles;
import domain.Actor;
import domain.Member;
import domain.Profile;
import domain.Request;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository	memberRepository;


	public Collection<Member> findAll() {
		return this.memberRepository.findAll();
	}

	public Actor findActorByUserAccount(final int id) {
		return this.memberRepository.findActorByUserAccount(id);
	}

	public Member findOne(final int id) {
		return this.memberRepository.findOne(id);
	}

	public Member findByUserAccount(final int userAccount) {
		Member member;
		member = this.memberRepository.findByUserAccount(userAccount);
		Assert.notNull(member);
		return member;
	}
	public Member createMember() {

		Member member;
		member = new Member();

		//authority
		Authority auth;
		auth = new Authority();
		Collection<Authority> authorities;
		authorities = new ArrayList<>();
		auth.setAuthority(Authority.MEMBER);
		authorities.add(auth);

		//account
		UserAccount account;
		account = new UserAccount();
		account.setEnabled(true);
		account.setUsername("");
		account.setPassword("");
		account.setAuthorities(authorities);
		member.setAccount(account);
		member.setProfiles(new ArrayList<Profile>());

		//store:
		member.setName("");
		member.setMiddleName("");
		member.setSurname("");
		member.setPhoto("");
		member.setEmail("");
		member.setPhone("");
		member.setAdress("");

		member.setRequests(new ArrayList<Request>());
		return member;
	}

	public Member save(final Member member) {

		if (member.getId() != 0) {
			final UserAccount account = this.findOne(member.getId()).getAccount();
			account.setUsername(member.getAccount().getUsername());
			account.setPassword(Utiles.hashPassword(member.getAccount().getPassword()));
			account.setAuthorities(member.getAccount().getAuthorities());
			member.setAccount(account);
		} else
			member.getAccount().setPassword(Utiles.hashPassword(member.getAccount().getPassword()));
		//final Collection<Box> boxes = this.boxService.save(Utiles.initBoxes());
		//member.setBoxes(boxes);

		Member modify;
		modify = this.memberRepository.saveAndFlush(member);
		return modify;
	}

}
