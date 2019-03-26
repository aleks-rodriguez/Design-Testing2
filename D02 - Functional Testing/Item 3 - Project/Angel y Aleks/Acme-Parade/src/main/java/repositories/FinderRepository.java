
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Finder;
import domain.Parade;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select m.finder from Member m where m.account.id = ?1")
	Finder findFinderFromMemberUA(int id);

	@Query("select p from Parade p where p.finalMode = true")
	Collection<Parade> findAllParades();
}
