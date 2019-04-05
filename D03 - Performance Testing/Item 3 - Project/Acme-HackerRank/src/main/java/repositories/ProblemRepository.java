
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findCompanyByUserAccountId(int id);

	@Query("select p from Problem p where p.company.id = ?1")
	Collection<Problem> getProblemByCompanyId(int id);
}
