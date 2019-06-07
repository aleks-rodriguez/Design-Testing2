
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
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	ActorService				actorService;

	@Autowired
	CustomisationSystemService	customService;


	/*
	 * TEST 1
	 * Requirement tested: An actor who is authenticated as an administrator must be able to display a dashboard
	 * - Analysis of sentence coverage of CustomisationSystemService: 47.3%
	 * Total instructions: 581; Covered Instructions: 275
	 * 
	 * - Analysis of data coverage: 0%
	 */

	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Positive test
				"admin1", null
			}, {
				//Negative test
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

			this.customService.CompanyRookies();

			this.customService.marcadorNumerico();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
