
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
public class AnswerServiceTest extends AbstractTest {

	@Autowired
	ActorService	actorService;

	@Autowired
	AnswerService	answerService;


	/*
	 * TEST 1
	 * Requirement tested: An actor who is authenticated as an rookie can create an answer about his application which is in final mode.
	 * - Analysis of sentence coverage of CustomisationSystemService: 26.9%
	 * Total instructions: 253; Covered Instructions: 53.1%
	 * 
	 * - Analysis of data coverage: 33.33%
	 * Attribute: explanation | Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: link| Bad value: - | Normal value: Yes | Coverage: 50% |
	 */

	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Positive test 
				"rookie1", "application1", null
			}, {
				//Negative test
				"company1", "application1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void template1(final String username, final int idApplication, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.answerService.createAnswer(idApplication);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
