
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import repositories.CompanyRepository;
import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Company;
import domain.CreditCard;
import domain.Finder;
import domain.Position;
import domain.Profile;
import domain.Rookie;
import forms.ActorForm;

@Service
@Transactional
public class ActorService extends AbstractService {

	@Autowired
	private AdministratorRepository	adminRepository;
	@Autowired
	private RookieRepository		rookieRepository;
	@Autowired
	private CompanyRepository		companyRepository;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private UserAccountRepository	userRepository;
	@Autowired
	private Validator				validator;
	@Autowired
	private BoxService				boxService;


	public Collection<Actor> getActorSpammer() {
		return this.adminRepository.getActorsSpammer();
	}

	public Collection<Actor> getActorEmabled() {
		return this.adminRepository.getActorsEnabled();
	}

	public Administrator findFirstAdmin() {
		return this.adminRepository.findFirstAdmin().get(0);
	}

	public Actor findByUserAccount(final int id) {
		Actor res;
		if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN))
			res = this.adminRepository.findAdminByUserAccountId(id);
		else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			res = this.rookieRepository.findRookieByUserAccount(id);
		else
			res = this.companyRepository.findCompanyByUserAccount(id);
		return res;
	}

	public CreditCard createCreditCard() {
		CreditCard creditCard;
		creditCard = new CreditCard();
		creditCard.setHolder("");
		creditCard.setMake("");
		creditCard.setNumber("");
		creditCard.setExpiration(new Date());
		creditCard.setCvv(0);

		return creditCard;
	}

	private String hashPassword(final String old) {
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		String passEncoded;
		passEncoded = encoder.encodePassword(old, null);

		return passEncoded;
	}
	public <T extends Actor> void setBasicProperties(final T actor, final String auth) {
		actor.setProfiles(new ArrayList<Profile>());
		actor.setAdress("");
		actor.setBoxes(new ArrayList<Box>());
		actor.setEmail("");
		actor.setName("");
		actor.setPhone("");
		actor.setPhoto("");
		actor.setSurname("");
		actor.setSpammer(false);
		actor.setCreditCard(this.createCreditCard());
		actor.setAccount(this.userAccountAdapted("", "", auth));
	}

	public Actor createActor(final String auth) {
		Actor actor;

		if (auth.equals(Authority.ADMIN)) {
			Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
			Administrator administrator;
			administrator = new Administrator();
			this.setBasicProperties(administrator, auth);
			actor = administrator;
		} else if (auth.equals(Authority.ROOKIE)) {
			Rookie rookie;
			rookie = new Rookie();
			this.setBasicProperties(rookie, auth);
			actor = rookie;
		} else if (auth.equals(Authority.COMPANY)) {
			Company company;
			company = new Company();
			this.setBasicProperties(company, auth);
			company.setCommercialName("");
			actor = company;
		} else
			actor = null;
		return actor;

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
		final Collection<Box> boxes = this.boxService.save(super.initBoxes());
		actor.setBoxes(boxes);
	}

	public Actor save(final Administrator admin, final Rookie rookie, final Company company) {
		Actor saved = null;

		if (admin != null) {
			if (admin.getId() != 0)
				Assert.isTrue(LoginService.getPrincipal().getId() == admin.getAccount().getId());
			Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));

			if (admin.getId() == 0) {
				UserAccount user;
				user = this.userRepository.save(admin.getAccount());
				admin.setAccount(user);
			}

			saved = this.adminRepository.save(admin);
		} else if (rookie != null) {
			if (rookie.getId() != 0)
				Assert.isTrue(LoginService.getPrincipal().getId() == rookie.getAccount().getId());

			if (rookie.getId() == 0) {
				UserAccount user;
				user = this.userRepository.save(rookie.getAccount());
				rookie.setAccount(user);
			}

			saved = this.rookieRepository.save(rookie);
		} else if (company != null) {
			if (company.getId() != 0)
				Assert.isTrue(LoginService.getPrincipal().getId() == company.getAccount().getId());

			if (company.getId() == 0) {
				UserAccount user;
				user = this.userRepository.save(company.getAccount());
				company.setAccount(user);
			}

			saved = this.companyRepository.save(company);
		}
		return saved;
	}

	public <T extends Actor> void setToActor(final T result, final ActorForm actor) {

		result.setName(actor.getName());
		result.setAdress(actor.getAdress());
		result.setPhone(actor.getPhone());
		result.setPhoto(actor.getPhoto());
		result.setSurname(actor.getSurname());
		result.setEmail(actor.getEmail());
		result.setVat(actor.getVat());
		result.setCreditCard(actor.getCreditCard());

		if (result.getAccount().getId() == 0) {
			if (actor.getAccount().getPassword().equals(actor.getPassword2()))
				result.setAccount(this.userAccountAdapted(actor.getAccount().getUsername(), this.hashPassword(actor.getAccount().getPassword()), actor.getAuthority()));
		} else {
			UserAccount ua;
			ua = this.userRepository.findOne(result.getAccount().getId());
			if (actor.getAccount().getPassword().equals(actor.getPassword2())) {
				if (!ua.getUsername().equals(actor.getAccount().getUsername()))
					ua.setUsername(actor.getAccount().getUsername());
				ua.setPassword(this.hashPassword(actor.getAccount().getPassword()));
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

			form.setAccount(this.userAccountAdapted("", "", auth));

			form.setPassword2("");
			form.setPhone("");
			form.setSurname("");
			form.setPhoto("");
			form.setName("");
			form.setVat("");
			form.setCreditCard(this.createCreditCard());

			if (auth.equals(Authority.COMPANY))
				form.setCommercialName("");

		} else {
			form.setId(a.getId());
			form.setAdress(a.getAdress());
			form.setAuthority(auth);
			form.setEmail(a.getEmail());
			form.setSurname(a.getSurname());
			form.setPassword2("");
			form.setPhone(a.getPhone());
			form.setAccount(this.userAccountAdapted(a.getAccount().getUsername(), "", auth));
			form.setPhoto(a.getPhoto());
			form.setName(a.getName());
			form.setVat(a.getVat());
			form.setCreditCard(a.getCreditCard());
			if (auth.equals(Authority.COMPANY)) {
				final Company c = (Company) a;
				form.setCommercialName(c.getCommercialName());
			}
			form.setTerms(true);
		}

		return form;
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

	public Company reconstructCompany(final ActorForm actor, final BindingResult binding) {
		Company result = null;

		if (actor.getId() == 0) {
			result = (Company) this.createActor(Authority.COMPANY);
			this.setToActor(result, actor);
			result.setCommercialName(actor.getCommercialName());
			this.validator.validate(result, binding);
			if (!binding.hasErrors())
				this.setBoxes(result);
		} else {
			result = this.companyRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
		}

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	public Rookie reconstructRookie(final ActorForm actor, final BindingResult binding) {
		Rookie result = null;

		if (actor.getId() == 0) {
			result = (Rookie) this.createActor(Authority.ROOKIE);
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
			if (!binding.hasErrors()) {
				this.setBoxes(result);
				Finder finder;
				finder = this.finderService.save(this.finderService.create(), new ArrayList<Position>());
				result.setFinder(finder);

			}

		} else {
			result = this.rookieRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
		}
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void delete(final int actorId) {
		Assert.notNull(LoginService.getPrincipal().getUsername());
		Administrator admin;
		Rookie rookie;
		Company company;

		if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
			admin = this.adminRepository.findAdminByUserAccountId(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			admin.getAccount().setEnabled(false);
			admin.setName("anonymous");
			admin.setPhone("anonymous");
			admin.setSurname("anonymous");
			admin.setPhone("000000000");
			admin.setEmail("anonymous@email.anon");
			admin.setVat("anonymous");
			admin.setPhoto("http://anon.anon");
			this.adminRepository.save(admin);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
			company = this.companyRepository.findCompanyByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			company.getAccount().setEnabled(false);
			company.setName("anonymous");
			company.setPhone("anonymous");
			company.setSurname("anonymous");
			company.setPhone("000000000");
			company.setEmail("anonymous@email.anon");
			company.setCommercialName("anonymous");
			company.setVat("anonymous");
			company.setPhoto("http://anon.anon");
			this.companyRepository.save(company);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
			rookie = this.rookieRepository.findRookieByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			rookie.getAccount().setEnabled(false);
			rookie.setName("anonymous");
			rookie.setPhone("000000000");
			rookie.setSurname("anonymous");
			rookie.setPhone("anonymous");
			rookie.setEmail("anonymous@email.anon");
			rookie.setFinder(null);
			rookie.setVat("anonymous");
			rookie.setPhoto("http://anon.anon");
			this.rookieRepository.save(rookie);
		}
	}
	public void flushAdministrator() {
		this.adminRepository.flush();
	}
	public void flushRookie() {
		this.rookieRepository.flush();
	}
	public void flushCompany() {
		this.companyRepository.flush();
	}
}
