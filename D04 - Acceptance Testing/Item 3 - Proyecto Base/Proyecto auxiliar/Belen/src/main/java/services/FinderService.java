
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import domain.Finder;
import domain.Position;
import domain.Rookie;
import domain.Ticker;

@Service
@Transactional
public class FinderService extends AbstractService {

	@Autowired
	private FinderRepository	repository;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;


	public Finder findOne(final Finder finder) {
		return this.repository.findOne(finder.getId());
	}

	public Collection<Position> findBySingleKey(final String singleKey) {
		List<Position> result;

		result = new ArrayList<Position>(this.repository.findBySingleKey(singleKey));

		Integer finderSize;
		finderSize = Integer.valueOf(System.getProperty("finderSize"));

		if (result.size() > finderSize)
			result = result.subList(0, finderSize);

		return result;
	}

	public Collection<Position> findByLucene(final String singleWord, final Date deadline, final Double minSalary, final Double maxSalary) throws Throwable {
		List<Position> res;
		res = this.fullTextSearch(singleWord, deadline, minSalary, maxSalary);
		Integer finderSize;
		finderSize = Integer.valueOf(System.getProperty("finderSize"));

		if (res.size() > finderSize)
			res = res.subList(0, finderSize);
		return res;

	}

	public Finder save(final Finder finder, final Collection<Position> col) {
		//		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
		Finder result;
		result = this.repository.save(finder);
		if (finder.getId() != 0) {
			result.setPositions(col);
			result.setCreationDate(new Date());
		}
		return result;
	}

	public Finder create() {

		Finder finder;
		finder = new Finder();
		try {
			Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE));
			finder.setCreationDate(new Date());
			finder.setPositions(new ArrayList<Position>());
		} catch (final IllegalArgumentException e) {

			finder.setSingleKey("");

		}
		return finder;
	}

	public Finder oldFinder() {
		Finder res;
		res = null;
		try {
			if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
				final Rookie h = (Rookie) this.actorService.findByUserAccount(LoginService.getPrincipal().getId());
				res = h.getFinder();
			}
		} catch (final IllegalArgumentException e) {
		}
		return res;
	}

	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		Finder result;
		result = new Finder();

		try {
			Rookie h;
			h = (Rookie) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
			result = h.getFinder();
			result.setSingleKey(finder.getSingleKey());
			result.setDeadline(finder.getDeadline());
			result.setMinSalary(finder.getMinSalary());
			result.setMaxSalary(finder.getMaxSalary());
		} catch (final IllegalArgumentException e) {
			result = finder;
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void clear(final Finder aux) {
		aux.setPositions(new ArrayList<Position>());
		aux.setCreationDate(new Date());
	}

	private List<Position> fullTextSearch(final String s, final Date deadlineDate, final Double minSalary, final Double maxSalary) throws Throwable {
		List<Position> result;
		final HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
		final EntityManagerFactory entityManagerFactory = provider.createEntityManagerFactory("Acme-Rookies", null);
		final EntityManager em = entityManagerFactory.createEntityManager();
		final FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		em.getTransaction().begin();
		fullTextEntityManager.createIndexer().startAndWait();

		final QueryBuilder positionQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Position.class).get();
		final QueryBuilder tickerQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Ticker.class).get();

		final BooleanJunction<BooleanJunction> bj = positionQueryBuilder.bool();
		if (s != null) {
			final org.apache.lucene.search.Query singleKeyWord = positionQueryBuilder.keyword().fuzzy().onFields("title", "description", "skillsRequired", "technologies", "profileRequired").matching(s).createQuery();
			final org.apache.lucene.search.Query ticker = tickerQueryBuilder.keyword().fuzzy().onField("ticker").matching(s).createQuery();
			bj.must(singleKeyWord).should(ticker);
		}
		if (deadlineDate != null) {
			final org.apache.lucene.search.Query deadline = positionQueryBuilder.range().onField("deadline").below(deadlineDate).createQuery();
			bj.must(deadline);
		}
		if (minSalary != null) {
			final org.apache.lucene.search.Query minSalaryQuery = positionQueryBuilder.range().onField("salary").above(minSalary).createQuery();
			bj.must(minSalaryQuery);
		}
		if (maxSalary != null) {
			final org.apache.lucene.search.Query maxSalaryQuery = positionQueryBuilder.range().onField("salary").below(maxSalary).createQuery();
			bj.must(maxSalaryQuery);
		}
		final org.apache.lucene.search.Query finalAndNotCancel = positionQueryBuilder.bool().must(positionQueryBuilder.keyword().onField("finalMode").matching(true).createQuery())
			.must(positionQueryBuilder.keyword().onField("cancel").matching(false).createQuery()).createQuery();
		final javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(bj.must(finalAndNotCancel).createQuery(), Position.class);

		result = jpaQuery.getResultList();
		em.getTransaction().commit();
		em.close();
		entityManagerFactory.close();
		return result;
	}

}
