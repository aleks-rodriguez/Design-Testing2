
package repositories;

import java.util.ArrayList;
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
	//Query5
	@Query("select p from Parade p where function('datediff', p.momentOrganised, current_date()) <= 30 and p.finalMode = true")
	Collection<Parade> findProcessionsInLessThan30Days();
	//Query7-------
	@Query("select m from Member m join m.requests r where r.status = 'APPROVED' group by m having count(r)/(select count(r) from Member m join m.requests r) > 0.1")
	Collection<Member> moreThan10PercentRequestAccepted();
	//Query6
	@Query("select count(p)*1.0/(select count(r)*1.0 from Request r), p.status from Request p group by p.status")
	Collection<Object[]> findRatioRequestsToMarchByStatus();
	//Query4
	@Query("select p.title, a.status,(select count(r)*1.0 from Request r where r=a)/p.requests.size from Parade p join p.requests a where p.finalMode = true order by p.id")
	Collection<Object[]> requestToMarchOnEachProcession();
	//query1
	@Query("select sqrt((sum((select count(q)*1.0 from f.enrolments q where q.exitMember = false)*(select count(q)*1.0 from f.enrolments q where q.exitMember = false))/count(f)) - (select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false) * (select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false)) from Brotherhood f")
	Double enrolmentsMemberStdDev();
	//Query10
	@Query("select CASE WHEN ((count(f)*1.0/(select count(e)*1.0 from Finder e where e.parades.size > 0))*1.0) = null THEN 0.0 END from Finder f where f.parades.size = 0")
	Double emptyNonEmptyFinders();
	//query1
	@Query("select count(e)*1.0/(select count(b) from Brotherhood b) from Enrolment e where e.exitMember = false")
	Double enrolmentsMemberAvg();
	//query1
	@Query("select min(b.enrolments.size)*1.0, max(b.enrolments.size)*1.0 from Brotherhood b join b.enrolments e where e.exitMember = false")
	Double[] enrolmentsMemberMinMax();
	//Query9
	@Query("select min(f.parades.size)*1.0, max(f.parades.size)*1.0, avg(f.parades.size)*1.0, stddev(f.parades.size)*1.0 from Finder f")
	Double[] numberOfResultsFinder();
	//Area
	@Query("select count(b)*1.0/(select count(a) from Area a) from Brotherhood b")
	Double areaPerBrotherhoodRatio();
	//
	@Query("select a.name, (select count(b)*1.0 from Brotherhood b where b.area = a) from Area a")
	Collection<Object[]> countPerArea();
	//
	@Query("select (select count(b)*1.0 from Brotherhood b where b.area = a) as c from Area a order by c DESC")
	ArrayList<Double> minMaxPerArea();

	@Query("select (select count(a) from Area a)*1.0/count(b) from Brotherhood b")
	Double AVGBrotherhoddPerArea();

	@Query("select sqrt(sum((select count(b)*1.0 from Brotherhood b)*(select count(b)*1.0 from Brotherhood b))/count(a) - (select (select count(a) from Area a)*1.0/count(b) from Brotherhood b) * (select (select count(a) from Area a)*1.0/count(b) from Brotherhood b)) from Area a")
	Double stddevBrotherhoddPerArea();
	//mas grande y mas pequeña brotherhood
	@Query("select e.brotherhood.name from Enrolment e where e.exitMember = false group by e.brotherhood order by e.brotherhood desc")
	ArrayList<String> largestAndSmallerBro();
	//histogram
	@Query("select e.position.id, count(e.position)*1.0 from Enrolment e group by e.position")
	Collection<Object[]> histrogramDashboard();

	//Acme-Parade
	//Query1
	@Query("select avg(h.legalRecord.size+h.periodRecord.size+h.linkRecord.size+h.miscellaneousRecord.size+1), min(h.legalRecord.size+h.periodRecord.size+h.linkRecord.size+h.miscellaneousRecord.size+1), max(h.legalRecord.size+h.periodRecord.size+h.linkRecord.size+h.miscellaneousRecord.size+1), stddev(h.legalRecord.size+h.periodRecord.size+h.linkRecord.size+h.miscellaneousRecord.size+1) from History h")
	Double[] minMaxAvgDttvOfRecordPerBro();
	//Query2
	@Query("select br.name, (select (h.linkRecord.size+h.periodRecord.size+h.legalRecord.size+h.miscellaneousRecord.size) +count(h.inceptionRecord) from Brotherhood b join b.history h where h = br.history) as j from Brotherhood br order by j DESC")
	Collection<Object[]> largestBroHistory();
	//Query3
	@Query("select br.name from Brotherhood br where (select (h.linkRecord.size+h.periodRecord.size+h.legalRecord.size+h.miscellaneousRecord.size+1) from Brotherhood b join b.history h where h = br.history) > (select avg(h.legalRecord.size+h.periodRecord.size+h.linkRecord.size+h.miscellaneousRecord.size+1) from History h)")
	Collection<String> broMoreRecordThanAVG();
	//Query4
	@Query("select distinct ((select count(a)*1.0 from Area a)-(count(c.area)*1.0))/(select count(a)*1.0 from Area a)from Chapter c")
	Double ratioAreaNoCoordinateChapter();
	//Query5
	@Query("select max(b.parades.size), min(b.parades.size), avg(b.parades.size), stddev(b.parades.size) from Brotherhood b where (select c from Chapter c join c.area a where a = b.area) <> null")
	Double[] minMaxAVGsttdvParadePerChapter();
	//Query6
	@Query("select b.parades.size, (select c.name from Chapter c join c.area a where a = b.area) from Brotherhood b where b.parades.size > (select avg(b.parades.size) from Brotherhood b)+(select avg(b.parades.size) from Brotherhood b)*0.1")
	Collection<Object[]> chapter10PorcentMoreParadeThanAVG();
	//Query7
	@Query("select (select count(p)*1.0/(select count(p)*1.0 from Parade p) from Parade p where p.finalMode = true), count(p)*1.0/(select count(p)*1.0 from Parade p) from Parade p where p.finalMode = false")
	Double[] paradeFinalModevsParadeNoFinalMode();
	//Query8
	@Query("select count(p)*1.0/(select count(p)*1.0 from Parade p where p.finalMode = true), p.status from Parade p where p.finalMode = true group by p.status")
	Collection<Object[]> paradesFinalModeGroupByStatus();
	//Query9
	@Query("select count(s)*1.0/(select count(s) from Sponsorship s)from Sponsorship s where s.isActive = true")
	Double ratioActiveSponsorship();
	//Query10
	@Query("select max(s.sponsorship.size), min(s.sponsorship.size), avg(s.sponsorship.size), stddev(s.sponsorship.size) from Sponsor s join s.sponsorship ss where ss.isActive = true")
	Double[] minMaxAvgSttdvSponsorshipsPerSponsor();
	//Query11
	@Query("select distinct s.name from Sponsor s join s.sponsorship ss where ss.isActive = true order by ss.size DESC")
	ArrayList<String> top3Sponsor();
}
