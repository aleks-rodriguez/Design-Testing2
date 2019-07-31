
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
public class ItemServiceTest extends AbstractTest {

	@Autowired
	ActorService	actorService;

	@Autowired
	ItemService		itemService;


	/*
	 * TEST 1
	 * Requirement tested: An actor who is authenticated as an provider can delete his or her items.
	 * - Analysis of sentence coverage of ItemService: 14.7%
	 * Total instructions: 197; Covered Instructions: 29
	 * 
	 * - Analysis of data coverage: 50.0%%
	 * Attribute: name| Bad value: false | Normal value: Yes | Coverage: 50% |
	 * Attribute: description| Bad value: false | Normal value: Yes | Coverage: 50% |
	 * Attribute: urls| Bad value: - | Normal value: Yes | Coverage: 50% |
	 * Attribute: pictures| Bad value: - | Normal value: Yes | Coverage: 50% |
	 */

	@Test
	public void test1() {
		final Object testingData[][] = {
			{
				//Negative test
				"provider1", "item1", null
			}, {
				//Positive test
				"provider2", "item1", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void template1(final String username, final int itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.itemService.delete(itemId);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
