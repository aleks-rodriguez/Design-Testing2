
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Brotherhood;
import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select b from Brotherhood b where b.account.id = ?1")
	Brotherhood findBrotherhoodByUserAccountId(int id);

	@Query("select m.account from Member m join m.enrolments e join e.brotherhood b join b.processions p where p.id=?1")
	Collection<UserAccount> getProcessionMember(int procId);
}
