
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;
import domain.Sponsorship;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	@Query("select s from Sponsor s where s.account.id = ?1")
	Sponsor findByUserAccount(int idUserAccountId);

	@Query("select s.sponsorship from Sponsor s where s.id = ?1")
	Collection<Sponsorship> getSponsorshipsBySponsor(int id);
}
