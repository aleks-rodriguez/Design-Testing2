
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Parade;
import domain.Sponsorship;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Brotherhood b join b.parades p where p.status = 'ACCEPTED' and p.finalMode = true and b.id = ?1")
	Collection<Parade> findParadesByBrotherhoodId(int id);

	@Query("select p from Brotherhood b join b.parades p where p.status = 'ACEPTADO' and p.finalMode = true and b.id = ?1")
	Collection<Parade> findParadesByBrotherhoodIdES(int id);

	@Query("select p from Brotherhood b join b.parades p where p.finalMode = true and b.id = ?1")
	Collection<Parade> findParadesByBrotherhoodIdFM(int id);

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood findBrotherhoodByUserAccountId(int id);

	@Query("select b from Brotherhood b join b.parades p where p.id=?1")
	Brotherhood findBrotherhoodByParadesId(final int idParade);

	@Query("select p.ticker from Parade p")
	Collection<String> findAllTickersSystem();

	@Query("select p from Parade p where p.status = 'ACCEPTED' and p.finalMode = true")
	Collection<Parade> findParadesAFM();

	@Query("select s from Sponsorship s join s.parade p where p.id=?1")
	Collection<Sponsorship> findSponsorshipByParadeId(int idParade);
}
