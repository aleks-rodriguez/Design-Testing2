
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
import repositories.ChapterRepository;
import repositories.MemberRepository;
import repositories.SponsorRepository;
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
import domain.Chapter;
import domain.Enrolment;
import domain.Finder;
import domain.Float;
import domain.Member;
import domain.Profile;
import domain.Request;
import domain.Sponsor;
import domain.Sponsorship;
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
	private ChapterRepository		chapterRepository;

	@Autowired
	private BoxService				boxService;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private FinderService			serviceFinder;

	@Autowired
	private UserAccountRepository	repositoryUser;

	@Autowired
	private SponsorRepository		sponsorRepository;


	public Member findOneMember(final int id) {
		return this.memberRepository.findOne(id);
	}
	public Chapter findOneChapter(final int id) {
		return this.chapterRepository.findOne(id);
	}
	public Brotherhood findOneBrotherhood(final int id) {
		return this.brotherhoodRepository.findOne(id);
	}

	public Collection<Brotherhood> findAllBrotherhood() {
		return this.brotherhoodRepository.findAll();
	}

	public Actor findByUserAccount(final int id) {
		return this.brotherhoodRepository.getByUserAccount(id);
	}
	public Actor findActorByUserAccount(final int id) {
		return this.brotherhoodRepository.getActorByUserAccount(id);
	}

	public Member findMemberByUser(final int id) {
		return this.brotherhoodRepository.getMemberByUserAccount(id);
	}

	public Collection<Chapter> findAllChapters() {
		return this.chapterRepository.findAll();
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

	public <T extends Actor> void setBasicProperties(final T actor, final String auth) {
		actor.setProfiles(new ArrayList<Profile>());
		actor.setAdress("");
		actor.setBoxes(new ArrayList<Box>());
		actor.setEmail("");
		actor.setMiddleName("");
		actor.setName("");
		actor.setPhone("");
		actor.setPhoto("");
		actor.setSurname("");

		actor.setAccount(this.userAccountAdapted("", "", auth));
	}

	public Actor createActor(final String auth) {

		if (auth.equals(Authority.ADMIN)) {
			Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
			Administrator administrator;
			administrator = new Administrator();
			this.setBasicProperties(administrator, auth);
			return administrator;
		} else if (auth.equals(Authority.BROTHERHOOD)) {
			Brotherhood brotherhood;
			brotherhood = new Brotherhood();

			this.setBasicProperties(brotherhood, auth);

			brotherhood.setTitle("");
			brotherhood.setEstablishment(new Date());
			brotherhood.setPictures(new ArrayList<String>());

			brotherhood.setArea(new Area());
			brotherhood.setEnrolments(new ArrayList<Enrolment>());
			brotherhood.setFloats(new ArrayList<Float>());

			return brotherhood;
		} else if (auth.equals(Authority.MEMBER)) {
			Member member;
			member = new Member();
			this.setBasicProperties(member, auth);

			member.setRequests(new ArrayList<Request>());

			return member;
		} else if (auth.equals(Authority.CHAPTER)) {
			Chapter chapter;
			chapter = new Chapter();
			this.setBasicProperties(chapter, auth);
			chapter.setTitle("");
			return chapter;
		} else {
			Sponsor sponsor;
			sponsor = new Sponsor();
			this.setBasicProperties(sponsor, auth);
			sponsor.setSponsorship(new ArrayList<Sponsorship>());
			return sponsor;
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
	public void save(final Administrator admin, final Brotherhood brotherhood, final Member member, final Chapter chapter, final Sponsor sponsor) {
		if (admin != null) {
			Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
			this.adminRepository.save(admin);
		} else if (brotherhood != null)
			this.brotherhoodRepository.save(brotherhood);
		else if (member != null)
			this.memberRepository.save(member);
		else if (chapter != null)
			this.chapterRepository.save(chapter);
		else if (sponsor != null)
			this.sponsorRepository.save(sponsor);
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
			if (!binding.hasErrors() && (actor.getAccount().getPassword().equals(actor.getPassword2()))) {
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

	public Chapter reconstructChapter(final ActorForm actor, final BindingResult binding) {
		Chapter result;

		if (actor.getId() == 0) {
			result = (Chapter) this.createActor(Authority.CHAPTER);
			this.setToActor(result, actor);
			result.setTitle(actor.getTitle());
			this.validator.validate(result, binding);
			if (!binding.hasErrors()) {
				this.setBoxes(result);
				UserAccount user;
				user = this.repositoryUser.save(result.getAccount());
				result.setAccount(user);
			}
		} else {
			result = this.chapterRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			result.setTitle(actor.getTitle());
			this.validator.validate(result, binding);
		}

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
					result.setAccount(this.userAccountAdapted(actor.getAccount().getUsername(), Utiles.hashPassword(actor.getAccount().getPassword()), actor.getAuthority()));
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
			if (auth.equals(Authority.CHAPTER))
				form.setTitle("");

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
			if (auth.equals(Authority.CHAPTER)) {
				final Chapter c = (Chapter) a;
				form.setTitle(c.getTitle());
			}

		}

		return form;
	}

	//sponsor
	public Collection<Sponsorship> getSponsorshipsBySponsor(final Sponsor s) {

		Collection<Sponsorship> result;
		Assert.notNull(s);
		result = this.sponsorRepository.getSponsorshipsBySponsor(s.getId());
		Assert.isTrue(result.size() >= 0);
		return result;
	}

	public Sponsor reconstructSponsor(final ActorForm actor, final BindingResult binding) {

		Sponsor result = null;

		if (actor.getId() == 0) {
			result = (Sponsor) this.createActor(Authority.SPONSOR);
			this.setToActor(result, actor);
			result.setSponsorship(actor.getSponsorships());

			this.validator.validate(result, binding);
			if (!binding.hasErrors())
				this.setBoxes(result);
		} else {
			result = this.sponsorRepository.findOne(actor.getId());
			this.setToActor(result, actor);

			this.validator.validate(result, binding);
		}

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Sponsor findById(final int id) {
		return this.sponsorRepository.findOne(id);
	}

	public Sponsor findSponsorByUserAccount(final int userAccount) {
		Assert.notNull(userAccount);
		return this.sponsorRepository.findByUserAccount(userAccount);
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

}
