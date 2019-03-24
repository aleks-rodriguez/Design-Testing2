
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Chapter;
import domain.Enrolment;
import domain.Member;
import domain.Parade;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select m from Member m where m.account.id = ?1")
	Member getMemberByUserAccount(int id);
	@Query("select m from Chapter m where m.account.id = ?1")
	Chapter getChapterByUserAccount(int id);
	@Query("select a from Actor a where a.account.id = ?1")
	Member getActorByUserAccount(int id);

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood getByUserAccount(int id);

	@Query("select distinct e.member from Enrolment e where e.brotherhood.id = ?1 and e.exitMember = false")
	Collection<Member> getAllMemberByBrotherhood(int id);

	@Query("select b from Brotherhood b where b.area.id = ?1")
	Collection<Brotherhood> getBrotherhoodsByAreaId(int id);

	@Query("select distinct e.brotherhood from Enrolment e where e.member.id = ?1 and e.exitMember = false and e.exitMoment = null")
	Collection<Brotherhood> getAllBrotherhoodActiveByMemberId(int id);

	@Query("select distinct e.brotherhood from Enrolment e where e.member.id = ?1 and e.exitMember = true")
	Collection<Brotherhood> getAllBrotherhoodDisByMemberId(int id);

	@Query("select e from Enrolment e where e.member.id = ?1 and e.brotherhood.id = ?2")
	Enrolment getEnrolmentByMemberAndBrother(int idMember, int idBrotherhood);

	@Query("select p from Brotherhood b join b.parades p where b.id=?1 and p.finalMode=true")
	Collection<Parade> getFinalModeParadesByBrotherhoodId(int id);

}
