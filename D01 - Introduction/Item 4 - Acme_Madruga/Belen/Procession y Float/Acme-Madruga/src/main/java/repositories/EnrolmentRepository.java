
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolment;
import domain.Member;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	@Query("select m from Member m where m.account.id = ?1")
	Member findMemberByUserAccountId(int id);

	@Query("select e from Enrolment e where e.member.id = ?1")
	Collection<Enrolment> getEnrolmentsByAMember(int id);

	@Query("select e from Enrolment e where e.position = null and e.brotherhood.id = ?1")
	Collection<Enrolment> getEnrolmentWithoutPosition(final int idBrotherhood);
}
