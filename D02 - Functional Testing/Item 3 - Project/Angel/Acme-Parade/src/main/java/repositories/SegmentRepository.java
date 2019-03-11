
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;
import domain.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {

	@Query("select p from Parade p join p.segments s where s.id = ?1")
	Parade findParadeBySegment(int id);

}
