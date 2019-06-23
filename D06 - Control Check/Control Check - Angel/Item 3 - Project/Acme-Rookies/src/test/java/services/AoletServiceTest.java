
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Aolet;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AoletServiceTest extends AbstractTest {

	@Autowired
	private AoletService	service;


	@Test
	public void driver() {
		final Object[][] objects = {
			{ //Positive Case. Creation of aolets.
				"auditor1", 0, null
			}, {
				//Negative Case. Creating an aolet with an empty field
				"auditor1", super.getEntityId("aolet1"), ConstraintViolationException.class
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.template((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2]);
	}
	protected void template(final String username, final int aolet, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);

			Aolet a;

			if (aolet == 0) {
				a = this.service.create(super.getEntityId("audit1"));
				a.setDescription("TEST");
				a.setTitle("TEST");
				a.setImage("");
			} else {
				a = this.service.findOne(aolet);
				a.setTitle("");
			}

			this.service.save(a);

			this.service.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
