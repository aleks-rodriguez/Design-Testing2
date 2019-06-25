
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ticketable.GenericRepository;
import ticketable.Ticker;
import domain.Actor;
import domain.Omamek;

@Repository
public interface OmamekRepository extends GenericRepository<Omamek> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select a from Omamek a where a.audit.id = ?1 and a.finalMode = true")
	Collection<Omamek> findOmameksByAuditFM(int audit);

	@Query("select a from Omamek a where a.audit.id = ?1 and a.finalMode = false and a.audit.position.company.id = ?2")
	Collection<Omamek> findOmameksByAuditDraftMode(int audit, int company);

	@Query("select a.ticker from Omamek a where a.id = ?1")
	Ticker findTickerOmamek(int id);
}
