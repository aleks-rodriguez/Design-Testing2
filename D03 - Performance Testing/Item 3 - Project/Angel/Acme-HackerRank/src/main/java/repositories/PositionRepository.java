
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Company;
import domain.Position;
import domain.Problem;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> getPositionsByCompany(int id);

	@Query("select p from Position p where p.company.id = ?1 and p.finalMode = true and p.cancel = false")
	Collection<Position> getPublicPositionsByCompany(int id);

	@Query("select p from Position p where p.finalMode = true and p.cancel = false")
	Collection<Position> getPublicPositions();

	@Query("select p from Problem p join p.position pos where pos.id = ?1")
	Collection<Problem> problemsByPosition(int id);

	@Query("select c from Company c")
	Collection<Company> findAllCompanies();
}
