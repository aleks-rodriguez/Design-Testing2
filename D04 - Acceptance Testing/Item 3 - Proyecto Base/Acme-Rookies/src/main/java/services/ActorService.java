
package services;

import java.security.SecureRandom;
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
import repositories.AuditorRepository;
import repositories.CompanyRepository;
import repositories.ProviderRepository;
import repositories.RookieRepository;
import repositories.TickerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.Box;
import domain.Company;
import domain.CreditCard;
import domain.Curricula;
import domain.Finder;
import domain.Item;
import domain.MessageEntity;
import domain.Position;
import domain.Profile;
import domain.Provider;
import domain.Rookie;
import domain.Sponsorship;
import domain.Ticker;
import forms.ActorForm;

@Service
@Transactional
public class ActorService extends AbstractService {

	@Autowired
	private AdministratorRepository		adminRepository;
	@Autowired
	private RookieRepository			rookieRepository;
	@Autowired
	private CompanyRepository			companyRepository;
	@Autowired
	private ProviderRepository			providerRepository;
	@Autowired
	private AuditorRepository			auditorRepository;
	@Autowired
	private FinderService				finderService;
	@Autowired
	private UserAccountRepository		userRepository;
	@Autowired
	private Validator					validator;
	@Autowired
	private BoxService					boxService;
	@Autowired
	private ProfileService				profileService;
	@Autowired
	private MessageService				messService;
	@Autowired
	private CurriculaService			currService;
	@Autowired
	private EducationDataService		eduService;
	@Autowired
	private MiscellaneousDataService	miscService;
	@Autowired
	private PositionDataService			posDataService;
	@Autowired
	private PositionService				posService;
	@Autowired
	private TickerRepository			tickerRepository;
	@Autowired
	private ItemService					itemService;
	@Autowired
	private SponsorshipService			sponsorshipService;
	@Autowired
	private AuditService				auditService;


	public Collection<Actor> getActorSpammer() {
		return this.adminRepository.getActorsSpammer();
	}

	public CreditCard getCreditcardByActor(final int id) {
		return this.adminRepository.getCreditcardByActor(id);
	}

	public Collection<Actor> getActorEmabled() {
		return this.adminRepository.getActorsEnabled();
	}

	public Administrator findFirstAdmin() {
		return this.adminRepository.findFirstAdmin().get(0);
	}

	public Collection<Double> findAllScoresByCompany(final int companyId) {
		return this.companyRepository.findAllScoresByCompanyId(companyId);
	}

	public Actor findByUserAccount(final int id) {

		Actor res;

		if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN))
			res = this.adminRepository.findAdminByUserAccountId(id);
		else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE))
			res = this.rookieRepository.findRookieByUserAccount(id);
		else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY))
			res = this.companyRepository.findCompanyByUserAccount(id);
		else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER))
			res = this.providerRepository.findProviderByUserAccount(id);
		else
			res = this.auditorRepository.findAuditorByUserAccount(id);

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
		} else if (auth.equals(Authority.PROVIDER)) {
			Provider provider;
			provider = new Provider();
			this.setBasicProperties(provider, auth);
			provider.setMake("");
			actor = provider;

		} else {
			Auditor auditor;
			auditor = new Auditor();
			this.setBasicProperties(auditor, auth);
			actor = auditor;
		}
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

	public Actor save(final Administrator admin, final Rookie rookie, final Company company, final Provider provider, final Auditor auditor) {
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

		} else if (provider != null) {
			if (provider.getId() != 0)
				Assert.isTrue(LoginService.getPrincipal().getId() == provider.getAccount().getId());

			if (provider.getId() == 0) {
				UserAccount user;
				user = this.userRepository.save(provider.getAccount());
				provider.setAccount(user);
			}

			saved = this.providerRepository.save(provider);
		} else if (auditor != null) {
			if (auditor.getId() != 0)
				Assert.isTrue(LoginService.getPrincipal().getId() == auditor.getAccount().getId());

			if (auditor.getId() == 0) {
				UserAccount user;
				user = this.userRepository.save(auditor.getAccount());
				auditor.setAccount(user);
			}

			saved = this.auditorRepository.save(auditor);
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
			if (auth.equals(Authority.PROVIDER))
				form.setMake("");
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
			if (auth.equals(Authority.PROVIDER)) {
				final Provider p = (Provider) a;
				form.setMake(p.getMake());

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
	public Provider reconstructProvider(final ActorForm actor, final BindingResult binding) {
		Provider result = null;

		if (actor.getId() == 0) {
			result = (Provider) this.createActor(Authority.PROVIDER);
			this.setToActor(result, actor);
			result.setMake(actor.getMake());
			this.validator.validate(result, binding);
			if (!binding.hasErrors())
				this.setBoxes(result);
		} else {
			result = this.providerRepository.findOne(actor.getId());
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
		}

		if (binding.hasErrors())

			throw new ValidationException();
		return result;
	}

	public Auditor reconstructAuditor(final ActorForm actor, final BindingResult binding) {
		Auditor result = null;

		if (actor.getId() == 0) {
			result = (Auditor) this.createActor(Authority.AUDITOR);
			this.setToActor(result, actor);
			this.validator.validate(result, binding);
			if (!binding.hasErrors())
				this.setBoxes(result);
		} else {
			result = this.auditorRepository.findOne(actor.getId());
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
		Provider provider;
		Auditor auditor;

		if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
			admin = this.adminRepository.findAdminByUserAccountId(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			admin = (Administrator) this.deleteCommon(admin);
			this.adminRepository.save(admin);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
			company = this.companyRepository.findCompanyByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			this.anonimicePosition(company);
			company = (Company) this.deleteCommon(company);
			company.setCommercialName("loremipsum");
			;
			this.companyRepository.save(company);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
			rookie = this.rookieRepository.findRookieByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			rookie = (Rookie) this.deleteCommon(rookie);
			this.finderService.delete(rookie.getFinder());
			rookie.setFinder(null);
			this.deleteCurricula();
			this.rookieRepository.save(rookie);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
			provider = this.providerRepository.findProviderByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			this.deleteItems(provider);
			this.anonimiceSponsorships(provider);
			provider = (Provider) this.deleteCommon(provider);
			provider.setMake("loremIpsum");
			this.providerRepository.save(provider);
		} else if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR)) {
			auditor = this.auditorRepository.findAuditorByUserAccount(actorId);
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");
			this.anonimiceAudits(auditor);
			this.deleteCommon(auditor);
			this.auditorRepository.save(auditor);
		}
	}
	private void deleteProfile(final Actor a) {
		this.profileService.delete(a.getProfiles());
		a.setProfiles(new ArrayList<Profile>());
	}

	private void deleteMessage(final Actor a) {
		Collection<MessageEntity> mess;
		mess = this.messService.getMessagesByActor(a.getId());
		this.messService.delete(mess);
	}

	private void deleteBox(final Actor a) {
		this.boxService.delete(a.getBoxes());
		a.setBoxes(new ArrayList<Box>());
	}

	private void deleteCurricula() {
		Collection<Curricula> c;
		c = this.currService.findAllByRookie();
		for (final Curricula curr : c) {
			this.eduService.delete(curr.getEducationData());
			this.miscService.delete(curr.getMiscellaneousData());
			this.posDataService.delete(curr.getPositionsData());
		}
		this.currService.delete(c);
	}

	private void deleteItems(final Provider p) {
		Collection<Item> items;
		items = this.itemService.getItemsByProvider(p.getId());
		this.itemService.delete(items);
	}

	private String randomice() {
		SecureRandom random;
		random = new SecureRandom();
		return String.valueOf(random.nextLong());
	}

	private void anonimicePosition(final Company c) {
		Collection<Position> positions;
		positions = this.posService.getPositionsByCompany(c.getId());
		for (final Position p : positions) {
			p.setCancel(true);
			p.setCompany(c);
			p.setDeadline(new Date());
			p.setDescription("loremIpsum");
			p.setFinalMode(false);
			p.setProfileRequired("loremIpsum");
			p.setSalary(0.0);
			p.setSkillsRequired("loremIpsum");
			p.setTechnologies("loremIpsum");
			this.tickerRepository.delete(p.getTicker());
			p.setTicker(this.fakeTicker());
			p.setTitle("loremIpsum");
			this.posService.save(p, false);
		}
	}
	private Actor deleteCommon(final Actor a) {
		a.getAccount().setEnabled(false);
		a.setAccount(this.userAccountAdapted(this.randomice(), this.randomice(), Authority.ANONYMOUS));
		a.setName("loremipsum");
		a.setPhone("loremipsum");
		a.setSurname("loremipsum");
		a.setPhone("000000000");
		a.setEmail("loremipsum@email.loremipsum");
		a.setVat("loremipsum");
		a.setPhoto("http://loremipsum.loremipsum");
		a.setCreditCard(this.fakeCreditCard());

		this.deleteProfile(a);
		this.deleteMessage(a);
		this.deleteBox(a);

		return a;
	}

	private Ticker fakeTicker() {
		Ticker t;
		t = new Ticker();
		t.setTicker("ANON" + this.randomice().substring(0, 3));
		return t;
	}

	private CreditCard fakeCreditCard() {
		CreditCard c;
		c = this.createCreditCard();
		c.setCvv(999);
		c.setNumber("0000000000000000");
		c.setHolder("loremIpsum");
		c.setMake("loremIpsum");
		return c;
	}

	private void anonimiceSponsorships(final Provider p) {
		Collection<Sponsorship> sponsorships;
		sponsorships = this.sponsorshipService.getSponsorshipActiveByProviderId(p.getId());
		sponsorships.addAll(this.sponsorshipService.getSponsorshipDesactiveByProviderId(p.getId()));
		for (final Sponsorship s : sponsorships) {
			s.setBanner("http://lorem.ipsum");
			s.setFlat_rate(0.0);
			s.setIsActive(false);
			s.setTarget("http://lorem.ipsum");
			s.setCreditCard(this.fakeCreditCard());
		}
		this.sponsorshipService.save(sponsorships);
	}

	private void anonimiceAudits(final Auditor a) {
		Collection<Audit> audits;
		audits = this.auditService.findAllAuditsByAuditor(a.getId());
		for (final Audit au : audits) {
			au.setFinalMode(false);
			au.setMoment(new Date());
			au.setScore(0.0);
			au.setText("loremIpsum");
		}
		this.auditService.save(audits);
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
	public void flushProvider() {
		this.providerRepository.flush();
	}
	public void flushAuditor() {
		this.auditorRepository.flush();
	}
}
