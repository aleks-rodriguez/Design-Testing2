
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Proclaim;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProclaimServiceTest extends AbstractTest {

	@Autowired
	private ProclaimService	serviceProclaim;


	@Test
	public void driver() {
		// Pos 0 --> User Logged
		// Pos 1 --> Object 
		// Pos 2 --> Exception

		final Object[][] objects = {
			{
				"chapter1", 0, null
			}, {
				"chapter2", 0, null
			}, {
				"chapter1", super.getEntityId("proclaim2"), IllegalArgumentException.class
			}, {
				"chapter2", super.getEntityId("proclaim1"), IllegalArgumentException.class
			}
		};

		final Object[][] objectsForDelete = {
			{
				"chapter1", super.getEntityId("proclaim1"), IllegalArgumentException.class
			}, {
				"chapter2", super.getEntityId("proclaim2"), IllegalArgumentException.class
			}, {
				"chapter1", super.getEntityId("proclaim3"), null
			}, {
				"chapter2", super.getEntityId("proclaim4"), null
			}, {
				"chapter1", super.getEntityId("proclaim2"), IllegalArgumentException.class
			}, {
				"chapter2", super.getEntityId("proclaim1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < objects.length; i++) {
			System.out.println("Test Adiccion o Modificacion:" + i);
			this.template((String) objects[i][0], (int) objects[i][1], (Class<?>) objects[i][2], false);
			System.out.println("Test Eliminacion:" + i);
			this.template((String) objectsForDelete[i][0], (int) objectsForDelete[i][1], (Class<?>) objectsForDelete[i][2], true);
		}
	}

	protected void template(final String auth, final int id, final Class<?> expected, final boolean savedOrDelete) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(auth);
			Proclaim proclaim;
			proclaim = id == 0 ? this.serviceProclaim.createProclaim() : this.serviceProclaim.findOne(id);

			if (!savedOrDelete) {
				proclaim.setMoment(new Date());
				proclaim.setText("Pruebas de Test Unitario");
				this.serviceProclaim.save(proclaim);
			} else
				this.serviceProclaim.deleteProclaim(id);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(caught, expected);
	}
}
