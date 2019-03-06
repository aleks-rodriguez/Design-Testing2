
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import repositories.BrotherhoodRepository;
import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utilities.Utiles;
import domain.Actor;
import domain.Administrator;
import domain.Area;
import domain.Box;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Finder;
import domain.Float;
import domain.Member;
import domain.Profile;
import domain.Request;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	@Autowired
	private AdministratorRepository	adminRepository;

	@Autowired
	private MemberRepository		memberRepository;

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private BoxService				boxService;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private FinderService			serviceFinder;

	@Autowired
	private UserAccountRepository	repositoryUser;


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

	public Actor findByUserAccount() {
		return this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId());
	}

	public Actor createActor(final String auth) {

		if (auth.equals(Authority.ADMIN)) {
			Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));

			Administrator administrator;
			administrator = new Administrator();

			administrator.setProfiles(new ArrayList<Profile>());
			administrator.setAdress("");
			administrator.setBoxes(new ArrayList<Box>());
			administrator.setEmail("");
			administrator.setMiddleName("");
			administrator.setName("");
			administrator.setPhone("");
			administrator.setPhoto("");
			administrator.setSurname("");

			administrator.setAccount(this.userAccountAdapted("", "", auth));

			return administrator;
		} else if (auth.equals(Authority.BROTHERHOOD)) {
			Brotherhood brotherhood;
			brotherhood = new Brotherhood();
			brotherhood.setProfiles(new ArrayList<Profile>());
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
			brotherhood.setFloats(new ArrayList<Float>());

			brotherhood.setAccount(this.userAccountAdapted("", "", auth));

			return brotherhood;
		} else {
			Member member;
			member = new Member();
			member.setName("");
			member.setMiddleName("");
			member.setSurname("");
			member.setPhoto("");
			member.setEmail("");
			member.setPhone("");
			member.setAdress("");
			member.setProfiles(new ArrayList<Profile>());
			member.setRequests(new ArrayList<Request>());
			member.setEnrolments(new ArrayList<Enrolment>());

			member.setAccount(this.userAccountAdapted("", "", auth));

			return member;
		}

	}

	public UserAccount userAccountAdapted(final String username, final String password, final String auth) {

		UserAccount user;
		user = new UserAccount();
		user.setUsername(username);
		user.setPassword(password);
		user.setEnabled(true);

		Authority authority;
		authority = new Authority();
		authority.setAuthority(auth);

		Collection<Authority> authorities;
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		user.setAuthorities(authorities);

		return user;
	}

	public void setBoxes(final Actor actor) {
		final Collection<Box> boxes = this.boxService.save(Utiles.initBoxes());
		actor.setBoxes(boxes);
	}
	public void save(final Administrator admin, final Brotherhood brotherhood, final Member member) {
		if (admin != null) {
			Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
			this.adminRepository.save(admin);
		} else if (brotherhood != null)
			this.brotherhoodRepository.save(brotherhood);
		else
			this.memberRepository.save(member);
	}

	public Administrator reconstructAdministrator(final ActorForm actor, final BindingResult binding) {
		Administrator result = null;

		if (actor.getId() == 0) {
			result = (Administrator) this.createActor(Authority.ADMIN);
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
			if (!binding.hasErrors())
				this.setBoxes(result);
		} else {
			result = this.adminRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
		}

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Brotherhood reconstructBrotherhood(final ActorForm actor, final BindingResult binding) {

		Brotherhood result = null;

		if (actor.getId() == 0) {
			result = (Brotherhood) this.createActor(Authority.BROTHERHOOD);
			this.setToActor(result, actor);
			result.setPictures(actor.getPictures());
			result.setTitle(actor.getTitle());
			this.validator.validate(result, binding);
			if (!binding.hasErrors()) {
				this.setBoxes(result);
				result.setArea(null);
			}
		} else {
			result = this.brotherhoodRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			result.setPictures(actor.getPictures());
			result.setTitle(actor.getTitle());
			this.validator.validate(result, binding);
		}

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Member reconstructMember(final ActorForm actor, final BindingResult binding) {
		Member result = null;

		if (actor.getId() == 0) {
			result = (Member) this.createActor(Authority.MEMBER);
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
			if (!binding.hasErrors()) {
				this.setBoxes(result);
				Finder finder;
				finder = this.serviceFinder.save(this.serviceFinder.createFinder());
				result.setFinder(finder);
				UserAccount user;
				user = this.repositoryUser.save(result.getAccount());
				result.setAccount(user);
			}

		} else {
			result = this.memberRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
		}
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public <T extends Actor> void setToActor(final T result, final ActorForm actor) {

		result.setName(actor.getName());
		result.setAdress(actor.getAdress());
		result.setPhone(actor.getPhone());
		result.setPhoto(actor.getPhoto());
		result.setSurname(actor.getSurname());
		result.setEmail(actor.getEmail());
		result.setMiddleName(actor.getMiddleName());

		if (result.getAccount().getId() == 0)
			result.setAccount(this.userAccountAdapted(actor.getAccount().getUsername(), Utiles.hashPassword(actor.getAccount().getPassword()), actor.getAuthority()));
		else {
			UserAccount ua;
			ua = result.getAccount();
			if (actor.getAccount().getPassword().equals(actor.getPassword2()))
				if (!ua.getUsername().equals(actor.getAccount().getUsername()))
					result.setAccount(this.userAccountAdapted(ua.getUsername(), Utiles.hashPassword(ua.getPassword()), actor.getAuthority()));
				else {
					ua.setPassword(Utiles.hashPassword(actor.getAccount().getPassword()));
					result.setAccount(ua);
				}

		}

	}
	public ActorForm map(final Actor a, String auth) {

		ActorForm form;
		form = new ActorForm();

		if (auth == null) {
			auth = LoginService.getPrincipal().getAuthorities().toString();
			auth = auth.substring(1, auth.toString().length() - 1);
		}
		if (a.getId() == 0) {

			form.setAdress("");
			form.setAuthority(auth);
			form.setEmail("");
			form.setMiddleName("");

			form.setAccount(this.userAccountAdapted("", "", auth));

			form.setPassword2("");
			form.setPhone("");
			form.setSurname("");
			form.setPhoto("");
			form.setName("");
			if (auth.equals(Authority.BROTHERHOOD)) {
				form.setPictures(new ArrayList<String>());
				form.setTitle("");
			}

		} else {
			form.setId(a.getId());
			form.setAdress(a.getAdress());
			form.setAuthority(auth);
			form.setEmail(a.getEmail());
			form.setMiddleName(a.getMiddleName());
			form.setSurname(a.getSurname());
			form.setPassword2("");
			form.setPhone(a.getPhone());
			form.setAccount(this.userAccountAdapted(a.getAccount().getUsername(), "", auth));
			form.setPhoto(a.getPhoto());
			form.setName(a.getName());
			if (auth.equals(Authority.BROTHERHOOD)) {
				final Brotherhood b = (Brotherhood) a;
				form.setPictures(b.getPictures());
				form.setTitle(b.getTitle());
			}

		}

		return form;
	}
}
