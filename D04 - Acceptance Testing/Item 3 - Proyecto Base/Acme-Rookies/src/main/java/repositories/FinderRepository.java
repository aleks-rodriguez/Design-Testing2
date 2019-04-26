
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select p from Position p where p.finalMode = true and p.cancel = false and (p.title LIKE %?1% or p.description LIKE %?1% or p.profileRequired LIKE %?1% or p.skillsRequired LIKE %?1% or p.technologies LIKE %?1% or p.company.commercialName LIKE %?1%)")
	Collection<Position> findBySingleKey(String singleKey);

}
