
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Position;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findCompanyByUserAccountId(int id);

	@Query("select p from Problem p where p.company.id = ?1")
	Collection<Problem> getProblemByCompanyId(int id);

	@Query("select p from Problem p join p.position r where r.id= ?1")
	Collection<Problem> getProblemByPositionId(int idPosition);

	@Query("select r from Position r join r.company c where r.finalMode = 0 and c.id = ?1")
	Collection<Position> getPositionForProblem(final int idCompany);
}
