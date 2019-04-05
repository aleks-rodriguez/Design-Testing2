
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);
}
