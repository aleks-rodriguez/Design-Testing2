
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;
import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select ss from Sponsorship ss where ss.sponsor.id = ?1 and ss.isActive=true")
	List<Sponsorship> getSponsorshipBySponsor(int id);

	@Query("select ss.parade from Sponsorship ss where ss.sponsor.id =?1")
	List<Parade> getParadesBySponsor(int id);

	@Query("select ss from Sponsorship ss where ss.sponsor.id = ?1 and ss.isActive=false")
	List<Sponsorship> getSponsorshipsDeactivatedBySponsor(int id);

}
