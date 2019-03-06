
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select p.requests from Member m join m.enrolments e join e.brotherhood b join b.processions p where p.id=?1")
	Collection<Request> getRequestByProcessionId(int procId);

	@Query("select p.requests from Member m join m.enrolments e join e.brotherhood b join b.processions p where p.id=?1 and m.account.id=?2")
	Collection<Request> getRequestByProcessionIdAndMemberId(int procId, int memberId);

	@Query("select p from Procession p join p.requests r where r.id=?1")
	Procession getProcessionByRequestId(int reqId);
}
