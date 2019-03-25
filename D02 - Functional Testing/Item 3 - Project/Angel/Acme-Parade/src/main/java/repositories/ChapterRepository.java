
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	@Query("select a from Actor a where a.id = ?1")
	Actor findById(int id);
}
