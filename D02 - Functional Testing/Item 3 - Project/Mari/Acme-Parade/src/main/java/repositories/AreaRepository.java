
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select b from Brotherhood b where b.area.id = ?1")
	Collection<Brotherhood> getBrotherhoodsByAreaId(int id);

	@Query("select a from Brotherhood a where a.account.id = ?1")
	Brotherhood getBrotherhoodByUserAccountId(int id);

	@Query("select a from Chapter a where a.account.id = ?1")
	Chapter getChapterByUserAccountId(int id);
}
