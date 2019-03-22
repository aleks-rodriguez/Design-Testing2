
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood findBrotherhoodByUserAccountId(int id);

	@Query("select b from Brotherhood b join b.parades p where p.id=?1")
	Brotherhood findBrotherhoodByParadesId(final int idParade);

	@Query("select p from Parade p where p.status = 'ACCEPTED' and p.finalMode = true")
	Collection<Parade> findParadesAFM();
}
