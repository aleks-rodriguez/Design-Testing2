
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.CustomisationSystem;

@Repository
public interface CustomisationSystemRepository extends JpaRepository<CustomisationSystem, Integer> {

	@Query("select a from Actor a where a.id = ?1")
	Actor findActor(int id);

	@Query("select a from Actor a where a.account.enabled = 1")
	Collection<Actor> findAllSuspiciousActors();

	@Query("select a from Actor a where a.account.enabled = 0")
	Collection<Actor> findAllNoEnabledActors();

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	//DASHBOARD C

	///The average, the minimum, the maximum, and the standard deviation of the position per company
	@Query("select avg(1.0*(select count(p) from Position p where p.company.id = c.id)), max(1.0*(select count(p) from Position p where p.company.id = c.id)), min(1.0*(select count(p) from Position p where p.company.id = c.id)), stddev(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Double[] minMaxAvgDttvOfPositionPerCompany();

	///The average, the minimum, the maximum, and the standard deviation of the applications per rookie
	@Query("select avg(1.0*(select count(a) from Application a where a.rookie.id = c.id)), max(1.0*(select count(a) from Application a where a.rookie.id = c.id)), min(1.0*(select count(a) from Application a where a.rookie.id = c.id)), stddev(1.0*(select count(a) from Application a where a.rookie.id = c.id)) from Rookie c")
	Double[] minMaxAvgDttvOfApplicationPerRookie();

	///The companies that have offered more positions.
	@Query("select p.company.name from Position p group by p.company.name order by count(p) DESC")
	List<String> findCompanyMorePositions();

	//The rookies who have made more applications.
	@Query("select a.rookie.name from Application a group by a.rookie.name order by count(a) DESC")
	List<String> findRookiesMoreApplications();

	///The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	@Query("select avg(p.salary), min(p.salary), max(p.salary), stddev(p.salary) from Position p")
	Double[] minMaxAvgDttvOfSalaries();

	//The best and the worst position in terms of salary.

	@Query("select p.title from Position p where p.salary = (select min(p.salary) from Position p)")
	Collection<String> findTheWorstPositionSalary();

	@Query("select p.title from Position p where p.salary = (select max(p.salary) from Position p)")
	Collection<String> findTheBestPositionSalary();

	//DASHBOARD B

	//The average, the minimum, the maximum, and the standard deviation of the applications per rookie
	@Query("select avg(1.0*(select count(c) from Curricula c where c.rookie.id = h.id)), max(1.0*(select count(c) from Curricula c where c.rookie.id = h.id)), min(1.0*(select count(c) from Curricula c where c.rookie.id = h.id)), stddev(1.0*(select count(c) from Curricula c where c.rookie.id = h.id)) from Rookie h")
	Double[] minMaxAvgDttvOfCurriculaPerRookie();

	//The minimum, the maximum, the average, and the standard deviation of the number of results in the finders.
	@Query("select avg(f.positions.size)*1.0, max(f.positions.size)*1.0,  min(f.positions.size)*1.0,  stddev(f.positions.size)*1.0 from Finder f")
	Double[] numberOfResultsFinder();

	// The ratio of empty versus non-empty finders.
	@Query("select (((select count(e)*1.0 from Finder e where e.positions.size > 0)/count(f)*1.0)*1.0) from Finder f")
	Double emptyNonEmptyFinders();

	//LEVEL C
	//The avg, min, max, std of the audit score of the positions stored in the system
	@Query("select avg(1.0*(select avg(a.score) from Audit a where a.position.id = p.id)), max(1.0*(select avg(a.score) from Audit a where a.position.id = p.id)), min(1.0*(select avg(a.score) from Audit a where a.position.id = p.id)), stddev(1.0*(select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double[] minMaxAvgDttvOfAuditPerPosition();

	//The avg, min, max, std of the audit score of the companies that are registered in the system
	@Query("select avg(1.0*(select avg(a.score) from Audit a join a.position p where p.company.id = c.id)), max(1.0*(select avg(a.score) from Audit a join a.position p where p.company.id = c.id)), min(1.0*(select avg(a.score) from Audit a join a.position p where p.company.id = c.id)),stddev(1.0*(select avg(a.score) from Audit a join a.position p where p.company.id = c.id)) from Company c")
	Double[] minMaxAvgDttvOfAuditPerCompany();

	//The companies with the highest audit score
	@Query("select distinct c.name from Audit a join a.position p join p.company c order by a.score DESC")
	List<String> highestCompaniesAuditScore();

	//The  average  salary  offered  by  the  positions  that  have  the  highest average audit score
	@Query("select avg(a.position.salary) from Audit a group by a.position.salary having avg(1.0*a.score) = (select max(1.0*a.score) from Audit a))")
	List<Double> averageSalaryMaxAverageAuditScore();
	//LEVEL B

	//The avg, min, max, std of the number of items per provider
	@Query("select avg(1.0*(select count(i) from Item i where i.provider.id = p.id)), max(1.0*(select count(i) from Item i where i.provider.id = p.id)), min(1.0*(select count(i) from Item i where i.provider.id = p.id)), stddev(1.0*(select count(i) from Item i where i.provider.id = p.id)) from Provider p")
	Double[] minMaxAvgDttvOfItemPerProvider();

	//The top-5 providers in terms of total number of items provided
	@Query("select i.provider.name from Item i group by i.provider.name order by count(i) DESC")
	List<String> top5ProviderPerMaxItems();

	//LEVEL A

	//The avg, min, max, std of sponsorships per provider
	@Query("select avg(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)), max(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)), min(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)), stddev(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Double[] minMaxAvgDttvOfSponsorshipsPerProvider();

	//The avg, min, max, std of sponsorships per position
	@Query("select avg(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)), max(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)), min(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)), stddev(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)) from Position p")
	Double[] minMaxAvgDttvOfSponsorshipsPerPosition();

	//The providerswho have a number of sponsorships that is at least 10% above the average number of sponsorships per provider
	@Query("select p.name from Provider p where (select count(s) from Sponsorship s where s.provider.id = p.id) >= (select avg(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)) + avg(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id))*0.1 from Provider p)")
	List<String> providersSponsorshipMoreThan10PerCent();
}
