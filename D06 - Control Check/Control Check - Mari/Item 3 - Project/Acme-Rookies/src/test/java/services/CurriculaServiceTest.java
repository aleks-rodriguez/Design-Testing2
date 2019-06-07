
package services;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private EducationDataService		serviceEdu;

	@Autowired
	private PositionDataService			servicePos;

	@Autowired
	private MiscellaneousDataService	serviceMisc;


	/**
	 * TEST 1. Manage his or her curricula, which includes listing, showing, creating, updating, and deleting them.
	 * 
	 * Sentence Coverage. 13.3%
	 * Covered Instructions: 44
	 * Total: 332
	 * 
	 * Data Coverage. 52.78
	 * 
	 * Attribute Bad Value Good Value Coverage
	 * ===================================================================
	 * 
	 * fullName | - | Yes | 100%
	 * statement | - | Yes | 50%
	 * phoneNumber | - | Yes | 50%
	 * githubProfile | - | Yes | 50%
	 * LinkedInProfile | - | Yes | 50%
	 * Rookie | - | Yes | 100%
	 * 
	 * positionsData | Yes | No | 25%
	 * educationData | Yes | No | 25%
	 * miscellaneousData | Yes | No | 25%
	 * 
	 */

	@Test
	public void driver() {
		final Object[][] objects = {
			{ 	// Edit the records of the curricula
				"rookie1", "curricula1", null
			}, {
				// Edit fail the records of the curricula
				"", "curricula1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < objects.length; i++)
			this.template((String) objects[i][0], super.getEntityId((String) objects[i][1]), (Class<?>) objects[i][2]);
	}
	protected void template(final String auth, final int i, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(auth);

			//new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19");
			Curricula c;
			c = this.curriculaService.findOne(i);

			c.setGithubProfile("https://www.prueba.es");
			c.setLinkedInProfile("https://www.prueba2.es");
			c.setPhoneNumber("+96 362515953");
			c.setStatement("holaholaholahola");

			this.curriculaService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * TEST 1.1
	 * 
	 * Sentence Coverage. 33.3%
	 * Covered Instructions: 72
	 * Total: 216
	 * 
	 * Data Coverage. 35%
	 * 
	 * Attribute Bad Value Good Value Coverage
	 * ==========================================================================
	 * 
	 * degree - Yes 50%
	 * institution - Yes 50%
	 * mark - Yes 25%
	 * startDate - Yes 25%
	 * endDate - Yes 25%
	 */

	@Test
	public void driverEduData() {
		final Object[][] objects = {
			{ // Edit the records of the curricula
				"rookie1", super.getEntityId("curricula1"), null
			}, {
				// Edit fail the records of the curricula
				"", super.getEntityId("curricula1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.templateEduData((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2]);
	}

	protected void templateEduData(final String auth, final int i, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(auth);

			//new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19")
			Curricula c;
			c = this.curriculaService.findOne(i);

			//Create a EducationData
			EducationData edu;
			edu = this.serviceEdu.create();

			edu.setInstitution("pruebaJUNIT4");
			edu.setDegree("pruebaJUNit4");
			edu.setMark(10.0);
			edu.setStartDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/03/19"));
			edu.setEndDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19"));

			Assert.isTrue(c.getEducationData().contains(this.serviceEdu.save(edu, i)));

			this.serviceEdu.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * TEST 1.2
	 * 
	 * Sentence Coverage: 33.3
	 * Instructions Coverage: 68
	 * Total. 204
	 * 
	 * Data Coverage. 37.5
	 * 
	 * Attribute Bad Value Good Value Coverage
	 * ======================================================================================
	 * title - Yes 50%
	 * description - Yes 50%
	 * startDate - Yes 25%
	 * endDate - Yes 25%
	 * 
	 */

	@Test
	public void driverPosData() {
		final Object[][] objects = {
			{ // Edit the records of the curricula
				"rookie1", super.getEntityId("curricula1"), null
			}, {
				// Edit fail the records of the curricula
				"", super.getEntityId("curricula1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.templatePosData((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2]);
	}
	protected void templatePosData(final String auth, final int i, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(auth);

			//new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19")
			Curricula c;
			c = this.curriculaService.findOne(i);

			//Create a PositionData
			PositionData pos;
			pos = this.servicePos.create();

			pos.setTitle("testing");
			pos.setDescription("testing");
			pos.setStartDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/03/19"));
			pos.setEndDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19"));

			Assert.isTrue(c.getPositionsData().contains(this.servicePos.save(pos, i)));

			this.servicePos.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	/**
	 * Test 1.3
	 * 
	 * 
	 * Sentence Coverage. 33.3%
	 * Covered Instructions 60
	 * Total 180
	 * 
	 * 
	 * Data Coverage. 25%
	 * 
	 * text > (Bad Value: -);(Good Value: Yes) > 50%
	 * urls > (Bad Value: -);(Good Value: -) > 0%
	 * 
	 */

	@Test
	public void driverMiscData() {
		final Object[][] objects = {
			{ // Edit the records of the curricula
				"rookie1", super.getEntityId("curricula1"), null
			}, {
				// Edit fail the records of the curricula
				"", super.getEntityId("curricula1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < objects.length; i++)
			this.templateMiscData((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2]);
	}
	protected void templateMiscData(final String auth, final int i, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(auth);

			//new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/19")
			Curricula c;
			c = this.curriculaService.findOne(i);

			//Create a miscellaneousData
			MiscellaneousData d;
			d = this.serviceMisc.create();
			d.setText("pruebaJUNIT4");

			Assert.isTrue(c.getMiscellaneousData().contains(this.serviceMisc.save(d, i)));

			this.serviceMisc.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
