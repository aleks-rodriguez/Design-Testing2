
package services;

import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.Authority;
import security.LoginService;
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
	@Autowired
	private ParadeService			paradeService;


	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final int id) {
		return this.sponsorshipRepository.findOne(id);
	}
	public List<Sponsorship> getSponsorshipBySponsor(final int id) {
		return this.sponsorshipRepository.getSponsorshipBySponsor(id);
	}

	public List<Parade> getParadesBySponsor(final int id) {
		return this.sponsorshipRepository.getParadesBySponsor(id);
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

	public void save(final Collection<Sponsorship> col) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		this.sponsorshipRepository.save(col);
	}

	public Sponsorship save(final Sponsorship sponsorship) {

		Sponsor logged;
		logged = this.serviceActor.findSponsorByUserAccount(LoginService.getPrincipal().getId());

		Assert.notNull(logged);

		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.SPONSOR));
		Sponsorship saved;

		saved = this.sponsorshipRepository.save(sponsorship);

		if (!(logged.getSponsorship().contains(saved))) {
			Collection<Sponsorship> actorSponshorships;
			actorSponshorships = logged.getSponsorship();
			actorSponshorships.add(saved);
			logged.setSponsorship(actorSponshorships);
		}

		return saved;
	}

	public void delete(final int id) {
		final Sponsorship desactive;
		desactive = this.sponsorshipRepository.findOne(id);
		desactive.setIsActive(false);

	}

	public void reactivate(final int id) {
		final Sponsorship reactivate;
		reactivate = this.sponsorshipRepository.findOne(id);
		reactivate.setIsActive(true);
	}

	public Sponsorship reconstruct(final Sponsorship s, final BindingResult binding, final int paradeId) {
		Sponsorship result;
		if (s.getId() == 0) {
			result = s;
			Boolean check;
			check = Boolean.valueOf(Utiles.checkCreditCard(s.getCreditCard().getNumber())[1]);
			if ((s.getCreditCard().getMake().equals("0") || check.equals(false)))
				throw new ValidationException();
			else {
				result.setSponsor(this.serviceActor.findSponsorByUserAccount(LoginService.getPrincipal().getId()));
				result.setIsActive(true);
				result.setParade(this.paradeService.findOne(paradeId));
				this.validator.validate(result, binding);
			}

		} else {
			result = this.sponsorshipRepository.findOne(s.getId());
			result.setLinkTPage(s.getLinkTPage());
			result.setUrlBanner(s.getUrlBanner());

			result.setCreditCard(s.getCreditCard());
			this.validator.validate(result, binding);
		}
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public List<Sponsorship> getSponsorshipsDeactivatedBySponsor(final int id) {
		return this.sponsorshipRepository.getSponsorshipsDeactivatedBySponsor(id);
	}

}
