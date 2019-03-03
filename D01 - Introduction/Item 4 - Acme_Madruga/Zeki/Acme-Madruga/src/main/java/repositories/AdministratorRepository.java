
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.account.id = ?1")
	Administrator getAdministratorByUserAccountId(int id);

	//Necessary for StringToActorConverter
	@Query("select a from Actor a where a.id = ?1")
	Actor getActorById(int id);

}
