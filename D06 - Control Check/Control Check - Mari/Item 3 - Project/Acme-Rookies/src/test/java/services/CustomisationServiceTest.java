
package services;

/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomisationServiceTest extends AbstractTest {

	@Autowired
	ActorService				actorService;

	@Autowired
	CustomisationSystemService	customService;


	/*
	 * TEST Dashboard and TEST Customisation
	 * Requirement tested: An actor who is authenticated as an administrator must be able to display a dashboard
	 * - Analysis of sentence coverage of CustomisationSystemService: 46.2%
	 * Total instructions: 355; Covered Instructions: 164
	 * 
	 * 
	 * - Analysis of data coverage: 0%
	 */

	@Test
	public void dashboard() {
		final Object testingData[][] = {
			{
				//Test negativo 
				"admin1", null
			}, {
				//Test negativo 
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void template1(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.customService.BestWorstPositionSalary();

			this.customService.marcadorNumericoArray();

			this.customService.marcadorNumerico();

			this.customService.CompanyRookies();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void customisation() {
		final Object testingData[][] = {
			{
				//Test negativo 
				"admin1", "rookie2", null
			}, {
				//Test negativo 
				"rookie1", "rookie2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void template2(final String username, final int actorId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.customService.unBanActor(actorId);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
