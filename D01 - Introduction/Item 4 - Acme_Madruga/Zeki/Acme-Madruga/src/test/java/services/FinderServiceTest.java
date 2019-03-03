
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import utilities.Utiles;
import domain.Actor;
import domain.Finder;
import domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	serviceFinder;


	@Test
	public void testFinder() {
		super.authenticate("member1");
		final Actor a = this.serviceFinder.findByUserAccount(LoginService.getPrincipal().getId());
		final Member m = (Member) a;
		final Finder f = this.serviceFinder.findOne(m.getFinder().getId());
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			f.setMaximumDate(format.parse("2022-03-21"));
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utiles.resultsFinder = 10;
		final Finder saved = this.serviceFinder.save(f);
		System.out.println();
		Assert.isTrue(saved.getProcessions().size() >= 0);
		super.unauthenticate();
	}
}
