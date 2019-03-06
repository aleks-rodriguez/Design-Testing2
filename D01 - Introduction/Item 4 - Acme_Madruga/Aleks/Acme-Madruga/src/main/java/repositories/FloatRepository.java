
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Float;

@Repository
public interface FloatRepository extends JpaRepository<Float, Integer> {

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood findBrotherhoodByUserAccountId(int id);

}
