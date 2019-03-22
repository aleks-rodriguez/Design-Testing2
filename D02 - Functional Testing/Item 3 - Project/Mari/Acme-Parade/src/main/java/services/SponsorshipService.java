
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private Validator				validator;

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private ActorService			serviceActor;


	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final int id) {
		return this.sponsorshipRepository.findOne(id);
	}
	public Sponsorship createSponsorship(final Sponsor sponsor, final Parade parade) {
		Sponsorship result;
		result = new Sponsorship();

		result.setUrlBanner("");
		result.setCreditCard(Utiles.createCreditCard());
		result.setLinkTPage("");
		result.setSponsor(sponsor);
		result.setParade(parade);
		result.setIsActive(true);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {

		UserAccount login;
		login = LoginService.getPrincipal();

		Sponsor logged;
		logged = (Sponsor) this.serviceActor.findByUserAccount(login.getId());

		Assert.notNull(logged);

		Sponsorship saved;

		saved = this.sponsorshipRepository.save(sponsorship);

		return saved;
	}

	public void delete(final int id) {
		final Sponsorship desactive;
		desactive = this.sponsorshipRepository.findOne(id);
		desactive.setIsActive(false);
		desactive.setParade(null);

	}

	public List<Sponsorship> getSponsorshipBySponsor(final int id) {
		return this.sponsorshipRepository.getSponsorshipBySponsor(id);
	}

	public List<Parade> getParadesBySponsor(final int id) {
		return this.sponsorshipRepository.getParadesBySponsor(id);
	}

	public Sponsorship reconstruct(final Sponsorship s, final BindingResult binding) {
		Sponsorship result;
		if (s.getId() == 0)
			result = s;
		else {
			result = this.sponsorshipRepository.findOne(s.getId());
			result.setIsActive(s.getIsActive());
			result.setLinkTPage(s.getLinkTPage());
			result.setParade(s.getParade());
			result.setUrlBanner(s.getUrlBanner());
			result.setCreditCard(s.getCreditCard());
			result.setSponsor(s.getSponsor());

		}
		this.validator.validate(result, binding);
		return result;
	}

}
