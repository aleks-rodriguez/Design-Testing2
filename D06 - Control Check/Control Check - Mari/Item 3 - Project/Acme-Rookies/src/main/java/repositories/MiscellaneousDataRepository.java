
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Curricula;
import domain.MiscellaneousData;

@Repository
public interface MiscellaneousDataRepository extends JpaRepository<MiscellaneousData, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select c from Curricula c join c.miscellaneousData e where e.id = ?1")
	Curricula findCurriculaByMiscellaneousDataId(int id);
}
