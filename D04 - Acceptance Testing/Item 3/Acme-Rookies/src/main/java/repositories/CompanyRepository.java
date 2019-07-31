
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.account.id = ?1")
	Company findCompanyByUserAccount(int companyId);

	@Query("select a.score from Audit a join a.position p where p.company.id = ?1")
	Collection<Double> findAllScoresByCompanyId(final int idCompany);
}
