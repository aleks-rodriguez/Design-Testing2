
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select m from Member where m.account.id = ?1")
	Member getMemberByUserAccount(int id);
	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood getByUserAccount(int id);

	@Query("select m from Member m join m.enrolments e join e.brotherhood b where b.id = ?1")
	Collection<Member> getAllMemberByBrotherhood(int id);

	@Query("select b from Brotherhood b where b.area.id = ?1")
	Collection<Brotherhood> getBrotherhoodsByAreaId(int id);

	@Query("select distinct e.brotherhood from Enrolment e where e.member.id = ?1 and e.exitMember = false and e.exitMoment = null")
	Collection<Brotherhood> getAllBrotherhoodActiveByMemberId(int id);

	@Query("select distinct e.brotherhood from Enrolment e where e.member.id = ?1 and e.exitMember = true")
	Collection<Brotherhood> getAllBrotherhoodDisByMemberId(int id);

	@Query("select e from Enrolment e where e.member.id = ?1 and e.brotherhood.id = ?2")
	Enrolment getEnrolmentByMemberAndBrother(int idMember, int idBrotherhood);

}
