
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomisationSystemRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Actor;
import domain.CustomisationSystem;
import domain.Member;
import domain.Parade;
import domain.Position;

@Service
@Transactional
public class CustomisationSystemService {

	@Autowired
	private CustomisationSystemRepository	repositoryCustomisationSystem;

	@Autowired
	private PositionService					servicePosition;


	public void flagSpam(final int idActor) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repositoryCustomisationSystem.findActorByUserAccountId(idActor);
		a.setSpammer(true);
	}
	public CustomisationSystem findUnique() {
		return this.repositoryCustomisationSystem.findAll().get(0);
	}
	public Collection<Actor> findAllNoEnabledActors() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repositoryCustomisationSystem.findAllNoEnabledActors();
	}

	public CustomisationSystem save(final CustomisationSystem custom) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repositoryCustomisationSystem.save(custom);
	}
	public void banActor(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repositoryCustomisationSystem.findActor(id);
		a.getAccount().setEnabled(false);
	}

	public void unBanActor(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repositoryCustomisationSystem.findActor(id);
		a.getAccount().setEnabled(true);
	}

	public Map<String, Double> marcadorNumerico() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		result.put("EmptyVsNotEmpty", this.repositoryCustomisationSystem.emptyNonEmptyFinders());//numero
		/**/
		result.put("AVGEnrolmentMember", this.repositoryCustomisationSystem.enrolmentsMemberAvg());//numero (1)
		/**/
		result.put("StandarDesviationEnrolmentMember", this.repositoryCustomisationSystem.enrolmentsMemberStdDev());//numero (1)
		/**/
		result.put("AreaPerBrotherhoodRatio", this.repositoryCustomisationSystem.areaPerBrotherhoodRatio());//area
		/**/
		result.put("AVGBrotherhoodPerArea", this.repositoryCustomisationSystem.AVGBrotherhoddPerArea());//area
		/**/
		result.put("stddevBrotherhoodPerArea", this.repositoryCustomisationSystem.stddevBrotherhoddPerArea());//area
		result.put("ratioAreaNoCoordinateChapter", this.repositoryCustomisationSystem.ratioAreaNoCoordinateChapter());
		result.put("ratioActiveSponsorship", this.repositoryCustomisationSystem.ratioActiveSponsorship());
		return result;
	}

	public Map<String, Collection<? extends Actor>> dashboardActor() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Collection<? extends Actor>> result;
		result = new HashMap<String, Collection<? extends Actor>>();
		final Collection<Member> members = this.repositoryCustomisationSystem.moreThan10PercentRequestAccepted();
		/**/result.put("MemberMoreThan10PercentRequestAccepted", members);
		return result;
	}

	public Map<String, Collection<String>> top3() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Collection<String>> result;
		result = new HashMap<String, Collection<String>>();
		final ArrayList<String> sponsor = this.repositoryCustomisationSystem.top3Sponsor();
		final Collection<String> a = null;
		if (sponsor.size() == 0)
			result.put("top", a);
		else if (sponsor.size() == 1)
			result.put("top", sponsor.subList(0, 1));
		else if (sponsor.size() == 2)
			result.put("top", sponsor.subList(0, 2));
		else if (sponsor.size() > 2)
			result.put("top", sponsor.subList(0, 3));
		return result;
	}
	public Map<String, Collection<Parade>> dashboardProcession() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Collection<Parade>> result;
		result = new HashMap<String, Collection<Parade>>();
		final Collection<Parade> processions = this.repositoryCustomisationSystem.findProcessionsInLessThan30Days();
		/**/result.put("ProcessionsInLessThan30Days", processions);
		return result;
	}

	public Map<String, Map<String, Double>> ratioStatus() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.findRatioRequestsToMarchByStatus();
		for (final Object[] o : rep)
			result.put(o[1].toString(), (Double) o[0]);
		final Map<String, Map<String, Double>> result2;
		result2 = new HashMap<>();
		result2.put("RatioRequestToMarchByStatus", result);
		return result2;

	}

	public Map<String, Map<String, Map<String, Double>>> dashboardRatio() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result1;
		result1 = new HashMap<String, Double>();
		Map<String, Map<String, Double>> result2;
		result2 = new HashMap<String, Map<String, Double>>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.requestToMarchOnEachProcession();
		for (final Object[] o : rep) {
			result1.put(o[1].toString(), (Double) o[2]);
			result2.put(o[0].toString(), result1);
		}
		final Map<String, Map<String, Map<String, Double>>> result3;
		result3 = new HashMap<>();
		result3.put("RatioRequestToMarchOnEachProcession", result2);
		return result3;
	}

	public Map<String, Map<String, Double>> countPerArea() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.countPerArea();
		for (final Object[] o : rep)
			result.put(o[0].toString(), (Double) o[1]);
		final Map<String, Map<String, Double>> result2;
		result2 = new HashMap<>();
		result2.put("AreaPerBrotherhood", result);
		return result2;
	}

	public Map<String, Double> minmaxArea() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		if (!this.repositoryCustomisationSystem.minMaxPerArea().isEmpty()) {
			result.put("MaxAreaBro", this.repositoryCustomisationSystem.minMaxPerArea().get(0));
			result.put("MinAreaBro", this.repositoryCustomisationSystem.minMaxPerArea().get(this.repositoryCustomisationSystem.minMaxPerArea().size() - 1));
		} else {
			result.put("MaxAreaBro", 0.0);
			result.put("MinAreaBro", 0.0);
		}
		return result;

	}

	public Map<String, String> largestAndSmallerBro() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, String> result;
		result = new HashMap<String, String>();
		if (!this.repositoryCustomisationSystem.largestAndSmallerBro().isEmpty()) {
			result.put("LargestBro", this.repositoryCustomisationSystem.largestAndSmallerBro().get(0));
			result.put("SmallerBro", this.repositoryCustomisationSystem.largestAndSmallerBro().get(this.repositoryCustomisationSystem.largestAndSmallerBro().size() - 1));
		} else {
			result.put("LargestBro", "");
			result.put("SmallerBro", "");
		}
		return result;
	}

	public Map<String, String> largestBroHistory() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		ArrayList<String> result;
		result = new ArrayList<>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.largestBroHistory();
		for (final Object[] o : rep)
			result.add(o[0].toString());
		final Map<String, String> result2;
		result2 = new HashMap<>();
		if (!result.isEmpty())
			result2.put("LargestBro", result.get(0));
		else
			result2.put("LargestBro", "");
		return result2;
	}

	public Map<String, Collection<String>> broMoreRecordThanAVG() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Collection<String>> result;
		result = new HashMap<String, Collection<String>>();
		final Collection<String> s = this.repositoryCustomisationSystem.broMoreRecordThanAVG();
		result.put("BroMoreThanAVG", s);
		return result;
	}

	public Map<String, Collection<String>> chapter10PorcentMoreParadeThanAVG() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		ArrayList<String> result;
		result = new ArrayList<>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.chapter10PorcentMoreParadeThanAVG();
		System.out.println(rep);
		System.out.println(rep.toString());
		for (final Object[] o : rep)
			if (o[1] != null)
				result.add(o[1].toString());
			else if (o[1] == null)
				result.add("");
		final Map<String, Collection<String>> result2;
		result2 = new HashMap<>();
		result2.put("LargestBro", result);

		return result2;
	}

	public Map<String, Map<String, Double>> paradesFinalModeGroupByStatus() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		final Collection<Object[]> rep = this.repositoryCustomisationSystem.paradesFinalModeGroupByStatus();
		for (final Object[] o : rep)
			result.put(o[1].toString(), (Double) o[0]);
		final Map<String, Map<String, Double>> result2;
		result2 = new HashMap<>();
		result2.put("parades", result);
		return result2;
	}

	public Map<String, Double> getPositionsCount(final String lang) {
		Map<String, Double> result;

		result = new HashMap<String, Double>();

		final Collection<Position> all = this.servicePosition.findAll();

		final Collection<Object[]> repo = this.repositoryCustomisationSystem.histrogramDashboard();

		for (final Object[] o : repo)
			if (lang == null) {
				result.put(this.servicePosition.findOne((int) o[0]).getName(), (Double) o[1]);
				System.out.println(o[0]);
				System.out.println(o[1]);
			} else
				result.put(String.valueOf(this.servicePosition.findOne((int) o[0]).getOtherLangs().toArray()[0]), (Double) o[1]);

		for (final Position p : all) {
			final String aux = (lang == null || lang == "en") ? p.getName() : String.valueOf(p.getOtherLangs().toArray()[0]);
			if (!result.containsKey(aux))
				if (lang == null)
					result.put(p.getName(), 0.0);
				else
					result.put(String.valueOf(p.getOtherLangs().toArray()[0]), 0.0);
		}
		return result;
	}

}