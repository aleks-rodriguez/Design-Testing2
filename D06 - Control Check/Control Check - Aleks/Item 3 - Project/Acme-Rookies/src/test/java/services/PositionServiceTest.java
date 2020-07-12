
package services;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	service;


	/**
	 * Test 1, 2, 3. Creation of Positions
	 * Requirement:Manage their positions, which includes listing, showing, creating, updating, and de-leting them.
	 * Positions can be saved in draft mode; they are not available publicly until they are saved in final mode.
	 * Once a position is saved in final mode, it cannot be further edited, but it can be cancelled.
	 * A position cannot be saved in final mode unless there are at least two problems associated with it.
	 * 
	 * Sentence Coverage: 50.8 %. Covered Instructions: 182. Missed Instructions: 176.
	 * 
	 * Data Coverage: 61.36 %
	 * 
	 * Attribute Bad value Good Value Coverage
	 * ================================================================================
	 * Ticker | null | Yes | 50%
	 * Title | - | Yes | 50%
	 * Description | - | Yes | 50%
	 * Deadline | - | Yes | 25%
	 * ProfileRequired | - | Yes | 50%
	 * SkillsRequired | - | Yes | 50%
	 * Technologies | - | Yes | 50%
	 * Salary | <0 | >0 | 100%
	 * finalMode | false | true | 100%
	 * Cancel | false | true | 100%
	 * company | null | Yes | 50%
	 * 
	 */

	@Test
	public void driverCreation() {

		/**
		 * Param 0 --> User logged
		 * Param 1 --> Object
		 * Param 2 --> Exception
		 */

		final Object[][] objects = {
			{ // Creation simple. Positive Case
				"company1", "", null
			}, { // Creation simple. Positive Case
				"company2", "", null
			}, {// Trying to save in final mode without 2 problems assigned. Negative Case.
				"company2", "nonSave", IllegalArgumentException.class
			}, {// Salary negative. Negative Case.
				"company2", "negative", ConstraintViolationException.class
			}, { // Creating a position with no logging. Negative Case.
				"", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < objects.length; i++)
			this.templateCreation((String) objects[i][0], objects[i][1], (Class<?>) objects[i][2]);
	}

	protected void templateCreation(final String username, final Object param, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			super.authenticate(username);

			Company c;
			c = (Company) this.service.findByUserAccount(LoginService.getPrincipal().getId());

			Position position;
			position = new Position();

			position.setTicker(this.service.createTicker(c.getCommercialName()));

			position.setDeadline(new SimpleDateFormat("yyyy/MM/dd").parse("2030/05/19"));
			position.setFinalMode((String) param == "nonSave" ? true : false);
			position.setDescription("hola1");

			if ((String) param == "negative")
				position.setSalary(-20.0);
			else
				position.setSalary(20.0);

			position.setSkillsRequired("hola1");
			position.setTechnologies("hola1");
			position.setTitle("hola1");
			position.setProfileRequired("hola1");
			position.setCancel(false);

			this.service.save(position, false);
			this.service.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdition() {

		/**
		 * Param 0 --> User logged
		 * Param 1 --> Object
		 * Param 2 --> Rookie
		 * Param 3 --> Exception
		 */

		final Object[][] objects = {
			{ // Edition in Draft Mode. Positive Test Case.
				"company1", super.getEntityId("position2"), null, ""
			}, { // Edition in Final Mode. Negative Test Case.
				"company1", super.getEntityId("position1"), IllegalArgumentException.class, ""
			}, { //Hacking trying to see a position from other company which mode is draft. Negative Test Case
				"company1", super.getEntityId("position3"), IllegalArgumentException.class, ""
			}, {
				// Set final mode with two problems assigned. Positive Test Case.
				"company2", super.getEntityId("position3"), null, "two"
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.templateEdition((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2], (String) objects[i][3]);
	}

	protected void templateEdition(final String auth, final int position, final Class<?> expected, final String param) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(auth);

			Position p;
			p = this.service.findOne(position);

			p.setDescription("editando");

			if (param.equals("two"))
				p.setFinalMode(true);

			this.service.save(p, false);

			this.service.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeletion() {

		/**
		 * Param 0 --> User logged
		 * Param 1 --> Object
		 * Param 2 --> Exception
		 */

		final Object[][] objects = {
			{ // Deleting a position. Positive Case
				"company1", super.getEntityId("position2"), null
			}, {
				//Deleting a position whose mode is final. Negative Case
				"company1", super.getEntityId("position1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.templateDelete((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2]);
	}

	protected void templateDelete(final String string, final int i, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(string);

			this.service.delete(i);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
