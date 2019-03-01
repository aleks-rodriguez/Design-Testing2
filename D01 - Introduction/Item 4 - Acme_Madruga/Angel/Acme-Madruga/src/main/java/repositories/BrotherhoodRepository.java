
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

	@Query("select m from Member m join m.enrolments e join e.brotherhood b where b.id = ?1")
	Collection<Member> getAllMemberByBrotherhood(int id);

	@Query("select b from Brotherhood b where b.area.id = ?1")
	Collection<Brotherhood> getBrotherhoodsByAreaId(int id);

}
