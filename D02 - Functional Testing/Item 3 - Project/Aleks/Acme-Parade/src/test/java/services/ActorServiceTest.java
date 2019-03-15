
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	serviceActor;


	@Test
	public void testCreateMember() {

	}
	@Test
	public void testCreateBrotherhood() {

	}
	@Test
	public void testCreateChapter() {

	}
	@Test
	public void testCreateAdministrator() {

	}
	@Test
	public void testUpdateEmailWrong() {

	}
	@Test
	public void testUpdateXSSHacking() {

	}
	@Test
	public void testUpdatePasswordLessThan() {

	}
	@Test
	public void testNotNullParameters() {

	}
	@Test
	public void testBanActor() {

	}
	@Test
	public void testUnBanActor() {

	}
	@Test
	public void testAssignedAreaBrotherhood() {

	}
	@Test
	public void testAssignedAreaChapter() {

	}
}
