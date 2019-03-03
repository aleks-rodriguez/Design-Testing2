
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select distinct e.requestedPosition from Enrolment e")
	Collection<Position> getPositionsRequested();

	@Query("select distinct e.assignededPosition from Enrolment e")
	Collection<Position> getGivenPositions();
}
