
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService	problemService;


	/*
	 * TEST 1
	 * Requirement tested: An actor who is authenticated as a company updating problems. Problems can be saved in draft mode; once they are saved in final mode, they cannot not be edited.
	 * - Analysis of sentence coverage of ProblemService: 24.1%
	 * Total instructions: 46; Covered Instructions: 191
	 * - Analysis of data coverage: 60.0%
	 * Attribute: FinalMode| Bad value: false | Normal value: Yes | Coverage: 100% |
	 * Attribute: title| Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: statement | Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: hint | Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: hint | Bad value: - | Normal value: Yes | Coverage: 50% |
	 */

	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Positive test
				"company1", "problem1", "hola", null
			}, {
				//Negative test
				"company1", "problem3", "hola", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void template(final String username, final int paradeId, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			Problem p;
			p = this.problemService.findOne(paradeId);
			p.setTitle(title);
			this.problemService.save(p);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
