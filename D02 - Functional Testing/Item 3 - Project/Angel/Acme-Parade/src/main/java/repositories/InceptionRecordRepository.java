
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.InceptionRecord;

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

	@Query("select h from History h join h.inceptionRecord i where i.id = ?1")
	InceptionRecord findHistoryByInceptionRecordId(final int idInception);
}
