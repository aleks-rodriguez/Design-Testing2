
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import utilities.Utiles;
import domain.Actor;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	serviceActor;


	/**
	 * Posible Test Cases
	 * + Constraints
	 * + UserAccount Data
	 * + Hacking. Editing Data from another actor.
	 * + Creation of Pre-Boxes
	 * + Edition of actors
	 */

	@Test
	public void driver() {
		//Pos 0 --> Auth for Edition
		//Pos 1 --> Exception
		//Pos 2 --> Hacked
		final Object[][] objects = {
			{
				"administrator1", null, ""
			}, {
				"administrator2", null, ""
			}, {
				"member1", null, ""
			}, {
				"member2", null, ""
			}, {
				"brotherhood1", null, ""
			}, {
				"brotherhood2", null, ""
			}, {
				"chapter1", null, ""
			}, {
				"member2", null, ""
			}
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void test1() {
		final int id = this.getEntityId("chapter1");
		super.authenticate("brotherhood1");

		final Chapter a = this.serviceActor.findOneChapter(id);
		a.setName("Nombre modificado jaja");
		final Actor saved = this.serviceActor.save(null, null, null, a);

		Assert.isTrue(a.getName() != saved.getName());
		super.unauthenticate();
	}

	/**
	 * Positive Test. A chapter is created.
	 */

	@Test
	public void test2() {
		Chapter c;
		c = (Chapter) this.serviceActor.createActor(Authority.CHAPTER);

		c.setName("Chapter 3");
		c.setAdress("Address Chapter 3");
		c.setEmail("chapter@mail.es");
		c.setMiddleName("");
		c.setPhone("+34 968516171");
		c.setTitle("Titulo del Chapter 3");

		c.getAccount().setUsername("chapter3");
		c.getAccount().setPassword(Utiles.hashPassword("chapter3"));

		Chapter saved;
		saved = (Chapter) this.serviceActor.save(null, null, null, c);
		System.out.println(saved.getId());
		System.out.println(saved.getAccount().getPassword());
		Assert.isTrue(saved.getId() != 0);
	}

}
