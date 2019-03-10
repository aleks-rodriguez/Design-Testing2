
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import utilities.DatabaseConfig;
import utilities.Utiles;
import domain.Actor;
import domain.Finder;
import domain.Parade;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	repositoryFinder;

	@Autowired(required = false)
	private Validator			validator;


	public Actor findByUserAccount(final int id) {
		return this.repositoryFinder.findActorByUserAccountId(id);
	}

	public Finder findOne(final int id) {
		Finder finder;
		finder = this.repositoryFinder.findOne(id);
		if (finder.getParades().size() > 0) {
			final Date creationFinder = finder.getCreationDate();
			final Date now = new Date();

			final long diff = now.getTime() - creationFinder.getTime();

			final long diffMinutes = diff / (60 * 1000);

			if (diffMinutes >= Utiles.hoursFinder * 60)
				finder.setParades(new ArrayList<Parade>());
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
		f.setParades(new ArrayList<Parade>());
		return f;
	}

	public Finder save(final Finder finder) {

		Finder saved;
		saved = this.repositoryFinder.save(finder);

		System.out.println("Entro en save 1");
		if (finder.getId() != 0) {
			final Collection<Parade> parades = this.findProcessionsByQuery(saved);
			System.out.println("Entro en if save 2");
			saved.setParades(parades);
			saved.setCreationDate(new Date());
		}
		System.out.println("Entro en save 2");

		if (finder.getId() == 0)
			saved.setArea(null);

		return saved;
	}

	private Collection<Parade> findProcessionsByQuery(final Finder finder) {
		StringBuilder builder;
		builder = new StringBuilder();
		builder.append("select p from Brotherhood b join b.parades p where p.finalMode = true AND");
		System.out.println("entro query 1");
		final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DatabaseConfig.PersistenceUnit);
		final EntityManager entityManager = emFactory.createEntityManager();

		if (finder.getSingleWord() != null && !finder.getSingleWord().equals(" ") && !finder.getSingleWord().equals(""))
			builder.append(" p.ticker LIKE CONCAT('%','" + finder.getSingleWord() + "','%') " + "OR p.description LIKE CONCAT('%','" + finder.getSingleWord() + "','%') " + "OR p.title LIKE CONCAT('%','" + finder.getSingleWord() + "','%') AND");

		final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (finder.getMinimunDate() != null)
			builder.append(" p.momentOrganised >= '" + format.format(finder.getMinimunDate()) + "' AND");
		if (finder.getMaximumDate() != null)
			builder.append(" p.momentOrganised <= '" + format.format(finder.getMaximumDate()) + "' AND");
		if (finder.getArea() != null)
			builder.append(" b.area.id = " + finder.getArea().getId() + " AND");

		String query = builder.toString();

		if (query.endsWith("AND"))
			query = query.substring(0, query.length() - 3);

		System.out.println("===========\nQUERY:" + query + " ============ \n\n");

		final TypedQuery<Parade> queryTQuery = entityManager.createQuery(query, Parade.class);

		List<Parade> result = queryTQuery.getResultList();
		System.out.println(result.size());

		if (result.size() > Utiles.resultsFinder)
			result = result.subList(0, Utiles.resultsFinder);

		if (result.size() < 1)
			result = new ArrayList<Parade>(this.repositoryFinder.findAllParades());

		return result;
	}
	public Finder reconstruct(final Finder aux, final BindingResult binding) {
		this.validator.validate(aux, binding);
		Finder res;
		if (!binding.hasErrors()) {
			res = this.repositoryFinder.findOne(aux.getId());
			res.setSingleWord(aux.getSingleWord());
			res.setArea(aux.getArea().getId() != 0 ? aux.getArea() : null);
			res.setCreationDate(new Date());
			res.setParades(new ArrayList<Parade>());
			res.setMinimunDate(aux.getMinimunDate());
			res.setMaximumDate(aux.getMaximumDate());

		} else
			res = aux;

		return res;
	}
}
