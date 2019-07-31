
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
public class AuditServiceTest extends AbstractTest {

	@Autowired
	ActorService	actorService;

	@Autowired
	AuditService	auditService;


	/*
	 * TEST 1
	 * Requirement tested: An actor who is authenticated as an auditor can delete his or her audits as long as it's
	 * saved in draft mode.
	 * - Analysis of sentence coverage of AuditService: 17.2%
	 * Total instructions: 256; Covered Instructions: 44
	 * 
	 * - Analysis of data coverage: 37.5%
	 * Attribute: moment| Bad value: false | Normal value: Yes | Coverage: 25% |
	 * Attribute: text | Bad value: false | Normal value: Yes | Coverage: 50% |
	 * Attribute: score | Bad value: false | Normal value: Yes | Coverage: 25% |
	 * Attribute: finalMode| Bad value: - | Normal value: Yes | Coverage: 50% |
	 */

	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Negative test
				"auditor1", "audit1", null
			}, {
				//Positive test
				"auditor2", "audit1", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void template1(final String username, final int auditId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.auditService.delete(auditId);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
