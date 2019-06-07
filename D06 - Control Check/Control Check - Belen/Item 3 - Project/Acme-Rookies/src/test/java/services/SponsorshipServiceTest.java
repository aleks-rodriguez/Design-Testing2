
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private PositionService		positionService;


	/*
	 * TEST 1,2,3
	 * Requirement tested: Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
	 * - Analysis of sentence coverage of SponsorshipService: 48.3%
	 * Total instructions: 300; Covered Instructions: 145
	 * - Analysis of data coverage: 40.0%
	 * Attribute: creditCard| Bad value: - | Normal value: - | Coverage: 0% |
	 * Attribute: banner| Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: target | Bad value: null | Normal value: Yes | Coverage: 100% |
	 * Attribute: flat_rate | Bad value: - | Normal value: - | Coverage: 0% |
	 * Attribute: isActive | Bad value: - | Normal value: Yes | Coverage: 50% |
	 */
	@Test
	public void testCreateSponsorship() {
		final Object testingData[][] = {
			{
				// Positive test
				"provider1", "provider1", "position2", true, "http://www.prueba.com", "http://www.prueba.com", null
			}, {
				// Negative test: Negative test: The business rule that has been violated: Target cannot be blank
				"provider1", "provider1", "position3", true, null, "http://www.prueba.com", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (boolean) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5],
				(Class<?>) testingData[i][6]);
	}

	protected void createSponsorship(final String username, final int sponsorId, final int positionId, final boolean isActive, final String target, final String urlBanner, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			//			Sponsor sp;
			Provider p;
			p = (Provider) this.actorService.findByUserAccount(LoginService.getPrincipal().getId());
			Position po;
			po = this.positionService.findOne(positionId);
			Sponsorship s;
			s = this.sponsorshipService.createSponsorship(p, po);
			s.setIsActive(isActive);
			s.setTarget(target);
			s.setBanner(urlBanner);
			CreditCard credit;
			credit = this.actorService.fakeCreditCard();
			s.setCreditCard(credit);
			s.setPosition(po);
			s.setProvider(p);
			this.sponsorshipService.save(s);
			this.sponsorshipService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void testUpdateSponsorship() {
		final Object testingData[][] = {
			{
				// Positive test
				"provider1", "sponsorship1", "http://www.hola.com", null
			}, {
				// Negative test: The business rule that has been violated: A sponsorship cannot be updated by another sponsor.
				"provider2", "sponsorship1", "http://www.troll.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.updateSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void updateSponsorship(final String username, final int sponsorshipId, final String target, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			Sponsorship s;
			s = this.sponsorshipService.findOne(sponsorshipId);
			s.setTarget(target);
			this.sponsorshipService.save(s);
			this.sponsorshipService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void testReactiveSponsorship() {
		final Object testingData[][] = {
			{
				// Positive test
				"provider1", "sponsorship2", null
			}, {
				// Negative test: The business rule that has been violated: A de-activated sponsorship can be re-activated later for the same sponsor.
				"provider2", "sponsorship2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.reactiveSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void reactiveSponsorship(final String username, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			this.sponsorshipService.reactivate(sponsorshipId);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
