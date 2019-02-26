
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FinderRepository;
import security.LoginService;
import utilities.DatabaseConfig;
import utilities.Utiles;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	repositoryFinder;


	public Actor findByUserAccount(final int id) {
		return this.repositoryFinder.findActorByUserAccountId(id);
	}

	public Finder memberFinder() {
		return ((Member) this.repositoryFinder.findActorByUserAccountId(LoginService.getPrincipal().getId())).getFinder();
	}

	public Finder findOne(final int id) {
		Finder finder;
		finder = this.repositoryFinder.findOne(id);
		if (finder.getProcessions().size() > 0) {
			final Date creationFinder = finder.getCreationDate();
			final Date now = new Date();

			final long diff = now.getTime() - creationFinder.getTime();

			final long diffMinutes = diff / (60 * 1000);

			if (diffMinutes >= Utiles.hoursFinder * 60)
				finder.setProcessions(new ArrayList<Procession>());
		}
		return finder;
	}

	public Finder createFinder() {
		Finder f;
		f = new Finder();
		f.setArea(null);
		f.setMaximumDate(new Date());
		f.setMinimunDate(new Date());
		f.setCreationDate(new Date());
		f.setSingleWord("");
		f.setProcessions(new ArrayList<Procession>());
		return f;
	}

	public Finder save(final Finder finder) {

		Finder saved;
		saved = this.repositoryFinder.save(finder);

		if (finder.getId() != 0) {
			if (finder.getArea() == null || finder.getArea().getId() == 0)
				finder.setArea(null);

			final Collection<Procession> processions = this.findProcessionsByQuery(finder);
			finder.setProcessions(processions);
			finder.setCreationDate(new Date());
		} else
			saved.setArea(null);

		return saved;
	}

	private Collection<Procession> findProcessionsByQuery(final Finder finder) {
		StringBuilder builder;
		builder = new StringBuilder();
		builder.append("select p from Brotherhood b join b.processions p where");

		final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DatabaseConfig.PersistenceUnit);
		final EntityManager entityManager = emFactory.createEntityManager();

		if (finder.getSingleWord() != null && !finder.getSingleWord().equals(" ") && !finder.getSingleWord().equals(""))
			builder.append(" p.ticker LIKE CONCAT('%','" + finder.getSingleWord() + "','%') " + "OR p.description LIKE CONCAT('%','" + finder.getSingleWord() + "','%') " + "OR p.address LIKE CONCAT('%','" + finder.getSingleWord() + "','%') AND");

		final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (finder.getMinimunDate() != null)
			builder.append(" p.momentOrganised >= '" + format.format(finder.getMinimunDate()) + "' AND");

		if (finder.getMaximumDate() != null)
			builder.append(" p.momentOrganised <= '" + format.format(finder.getMaximumDate()) + "' AND");

		if (finder.getArea() != null || finder.getArea().getId() != 0)
			builder.append(" b.area.id = " + finder.getArea().getId() + " AND");

		String query = builder.toString();

		if (query.endsWith("AND"))
			query = query.substring(0, query.length() - 3);

		System.out.println("===========\nQUERY:" + builder.toString() + " ============ \n\n");

		final TypedQuery<Procession> queryTQuery = entityManager.createQuery(query, Procession.class);

		List<Procession> result = queryTQuery.getResultList();

		if (result.size() > Utiles.resultsFinder)
			result = result.subList(0, Utiles.resultsFinder);

		if (result.size() < 1)
			result = new ArrayList<Procession>(this.repositoryFinder.findAllProcessions());

		return result;
	}
}
