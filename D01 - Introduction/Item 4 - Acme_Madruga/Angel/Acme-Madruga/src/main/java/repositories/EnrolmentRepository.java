
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Member;

public interface EnrolmentRepository extends JpaRepository<Member, Integer> {

	//	@Query("select e from Enrolment e where e.member.id = ?1")
	//	Collection<Enrolment> getEnrolmentsByAMember(int id);

}
