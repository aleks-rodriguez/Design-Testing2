
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Pelf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PelfServiceTest extends AbstractTest {

	@Autowired
	private PelfService	pelfService;


	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Positive test
				"company1", "audit1", "TEST", "http://www.test.com", true, null
			}, {
				//Negative test
				"company1", "audit1", "TEST", "dkdfs://www.test.com", true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[1][4], (Class<?>) testingData[i][5]);
	}

	protected void template(final String username, final int auditId, final String body, final String picture, final boolean finalMode, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			Pelf q;
			q = this.pelfService.create(auditId);
			q.setBody(body);
			q.setPicture(picture);
			q.setPublicationMoment(new Date());
			q.setTicker(this.pelfService.createTicker());
			q.setFinalMode(finalMode);
			this.pelfService.save(q);
			this.pelfService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
