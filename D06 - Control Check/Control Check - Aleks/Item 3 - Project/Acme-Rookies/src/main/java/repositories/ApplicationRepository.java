
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> getApplicationByRookieId(int idRookie);

	@Query("select a from Application a where a.rookie.id = ?1 and a.position.id = ?2")
	Collection<Application> getApplicationByPositionIdAndRookie(final int idRookie, final int idPosition);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findApplicationsByPositionId(int i);

}
