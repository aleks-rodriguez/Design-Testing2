
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Pelf;

@Repository
public interface PelfRepository extends JpaRepository<Pelf, Integer> {

	@Query("select q from Pelf q where q.company.id=?1 and q.audit.id = ?2")
	Collection<Pelf> findAllByCompany(int companyId, int auditId);

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select q.ticker.ticker from Pelf q")
	Collection<String> findAllTickers();

	@Query("select q from Pelf q where q.company.id = ?1")
	Collection<Pelf> findAllByCompanyId(int id);

	@Query("select q from Pelf q where q.audit.id=?1 and q.finalMode=true")
	Collection<Pelf> findAllFinalByAuditId(int id);
}
