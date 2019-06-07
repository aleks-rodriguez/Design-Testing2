
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Curricula;
import domain.PositionData;

@Repository
public interface PositionDataRepository extends JpaRepository<PositionData, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select c from Curricula c join c.positionsData e where e.id = ?1")
	Curricula findCurriculaByPositionDataId(int id);
}
