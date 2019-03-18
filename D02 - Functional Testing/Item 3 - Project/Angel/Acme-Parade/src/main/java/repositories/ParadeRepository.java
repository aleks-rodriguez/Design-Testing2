
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select b.parades from Brotherhood b join b.parades p where p.status = 'ACCEPTED' and p.finalMode = true and b.id = ?1")
	Collection<Parade> findParadesByBrotherhoodId(int id);

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood findBrotherhoodByUserAccountId(int id);

	@Query("select b from Brotherhood b join b.parades p where p.id=?1")
	Brotherhood findBrotherhoodByParadesId(final int idParade);

	@Query("select p.ticker from Parade p")
	Collection<String> findAllTickersSystem();
}
