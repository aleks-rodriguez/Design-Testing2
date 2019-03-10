
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.CustomisationSystem;
import domain.Member;
import domain.Parade;

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

	@Query("select p from Parade p where function('datediff', p.momentOrganised, current_date()) <= 30 and p.finalMode = true")
	Collection<Parade> findProcessionsInLessThan30Days();

	@Query("select f from Member f join f.requests t where f.requests.size > (select avg(f.requests.size)+(avg(f.requests.size)/10)*1.0 from Member f) and t.status = 'ACCEPTED'")
	Collection<Member> moreThan10PercentRequestAccepted();

	@Query("select count(p)*1.0/(select count(r)*1.0 from Request r), p.status from Request p group by p.status")
	Collection<Object[]> findRatioRequestsToMarchByStatus();

	@Query("select p.id, a.status,(select count(r)*1.0 from Request r where r=a)/p.requests.size from Parade p join p.requests a where p.finalMode = true order by p.id")
	String[] requestToMarchOnEachProcession();

	@Query("select sqrt((sum((select count(q)*1.0 from f.enrolments q where q.exitMember = false)*(select count(q)*1.0 from f.enrolments q where q.exitMember = false))/count(f)) - (select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false) * (select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false)) from Brotherhood f")
	Double enrolmentsMemberStdDev();

	@Query("select CASE WHEN ((count(f)*1.0/(select count(e)*1.0 from Finder e where e.parades.size > 0))*1.0) = null THEN 0.0 END from Finder f where f.parades.size = 0")
	Double emptyNonEmptyFinders();

	@Query("select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false")
	Double enrolmentsMemberAvg();

	@Query("select min(b.enrolments.size)*1.0, max(b.enrolments.size)*1.0 from Brotherhood b join b.enrolments e where e.exitMember = false")
	Double[] enrolmentsMemberMinMax();

	@Query("select min(f.parades.size)*1.0, max(f.parades.size)*1.0, avg(f.parades.size)*1.0, stddev(f.parades.size)*1.0 from Finder f")
	Double[] numberOfResultsFinder();
	//
	@Query("select count(b)*1.0/(select count(a) from Area a) from Brotherhood b")
	Double areaPerBrotherhoodRatio();

	@Query("select a.name, (select count(b)*1.0 from Brotherhood b where b.area = a) from Area a")
	Collection<Object[]> countPerArea();

	@Query("select a.name, (select count(b)*1.0 from Brotherhood b where b.area = a) as c from Area a order by c DESC")
	Collection<Object[]> minMaxPerArea();

	@Query("select (select count(a) from Area a)*1.0/count(b) from Brotherhood b")
	Double AVGBrotherhoddPerArea();

	@Query("select sqrt(sum((select count(b)*1.0 from Brotherhood b)*(select count(b)*1.0 from Brotherhood b))/count(a) - (select (select count(a) from Area a)*1.0/count(b) from Brotherhood b) * (select (select count(a) from Area a)*1.0/count(b) from Brotherhood b)) from Area a")
	Double stddevBrotherhoddPerArea();
	@Query("select e.position.id, count(e.position)*1.0 from Enrolment e group by e.position")
	Collection<Object[]> histrogramDashboard();
}
