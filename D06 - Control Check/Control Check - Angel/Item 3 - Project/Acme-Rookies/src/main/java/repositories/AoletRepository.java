
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ticketable.GenericRepository;
import domain.Actor;
import domain.Aolet;

@Repository
public interface AoletRepository extends GenericRepository<Aolet> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select a from Aolet a where a.audit.id = ?1 and a.finalMode = true")
	Collection<Aolet> findAoletsByAuditFM(int audit);

	@Query("select a from Aolet a where a.audit.id = ?1 and a.finalMode = false and a.audit.auditor.id = ?2")
	Collection<Aolet> findAoletsByAuditDraftMode(int audit, int auditor);
}
