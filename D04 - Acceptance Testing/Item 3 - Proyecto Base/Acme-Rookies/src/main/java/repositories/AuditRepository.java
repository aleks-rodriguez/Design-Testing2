
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Audit;
import domain.Position;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select a from Audit a where a.position.id = ?1")
	Collection<Audit> findAuditsByPosition(int id);

	@Query("select a from Audit a where a.position.id = ?1 and a.finalMode = true")
	Collection<Audit> findAuditsByPositionPublic(int id);

	@Query("select a from Audit a where a.auditor.id = ?1")
	Collection<Audit> findAuditsByAuditor(int id);

	@Query("select distinct a.position from Audit a where a.auditor.id = ?1")
	Collection<Position> findPositionByAuditor(int id);

	@Query("select a.score from Audit a join a.position p where p.company.id = ?1")
	Collection<Double> findAllScoresByCompanyId(final int idCompany);
}
