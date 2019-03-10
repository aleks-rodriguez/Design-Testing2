
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Parade;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findByUserAccount(int id);

	@Query("select p.requests from Parade p where p.id = ?1")
	Collection<Request> getRequestByProcessionId(int id);

	@Query("select p.requests from Enrolment e join e.brotherhood b join b.parades p where e.member.id = ?1 and p.id = ?2")
	Collection<Request> getRequestByProcessionIdAndMemberId(int memberId, int procId);

	@Query("select p from Parade p join p.requests r where r.id=?1")
	Parade getProcessionByRequestId(int reqId);

	@Query("select r.marchRow, r.marchColumn from Request r where r.status = 'APPROVED'")
	List<String> getOptimPosition();
}
