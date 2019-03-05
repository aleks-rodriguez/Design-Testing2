
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Member;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood getByUserAccount(int id);

	@Query("select distinct e.member from Enrolment e where e.brotherhood = ?1 and e.exitMember = false and e.position <> null")
	Collection<Member> getAllMemberByBrotherhood(int id);

}
