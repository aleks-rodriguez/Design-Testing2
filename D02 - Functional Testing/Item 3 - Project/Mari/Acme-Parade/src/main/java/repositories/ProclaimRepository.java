
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;
import domain.Proclaim;

@Repository
public interface ProclaimRepository extends JpaRepository<Proclaim, Integer> {

	@Query("select a from Chapter a where a.account.id = ?1")
	Chapter findByUserAccount(int id);

	@Query("select p from Proclaim p where p.chapter.id = ?1")
	Collection<Proclaim> findAllProclaimsByChapter(int id);

	@Query("select p from Proclaim p where p.chapter.id = ?1 and p.finalMode = true")
	Collection<Proclaim> findAllProclaimsByChapterFinalMode(int id);
}
