
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccount(int id);

	@Query("select b from Brotherhood b where b.area.id = ?1")
	Collection<Brotherhood> getBrotherhoodsByAreaId(int id);

	@Query("select a from Brotherhood a where a.account.id = ?1")
	Brotherhood getBrotherhoodByUserAccountId(int id);

	@Query("select a from Chapter a where a.account.id = ?1")
	Chapter getChapterByUserAccountId(int id);

	@Query("select c from Chapter c where c.area.id = ?1")
	Chapter findChapterIfAreaIsAssigned(int id);
}
