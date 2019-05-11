
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select c.flat_rate from CustomisationSystem c")
	Double getFlatRate();

	@Query("select c.vat from CustomisationSystem c")
	Double getVat();

	@Query("select s from Sponsorship s join s.provider p where p.id = ?1 and s.isActive = true")
	Collection<Sponsorship> getSponsorshipActiveByProviderId(final int idProvider);

	@Query("select s from Sponsorship s join s.provider p where p.id = ?1 and s.isActive = false")
	Collection<Sponsorship> getSponsorshipDesactiveByProviderId(final int idProvider);

	@Query("select s from Sponsorship s where s.provider.id = ?1 and s.position.id = ?2")
	Collection<Sponsorship> getSponsorshipByPositionId(final int idProvider, final int idPosition);

	@Query("select s from Sponsorship s where s.position.id = ?1")
	Collection<Sponsorship> getSponsorshipByPositionId(final int idPosition);
}
