
package services;

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
import domain.Position;
import domain.Parade;

@Service
@Transactional
public class CustomisationSystemService {

	@Autowired
	private CustomisationSystemRepository	repositoryCustomisationSystem;

	@Autowired
	private PositionService					servicePosition;


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
	public Map<String, String[]> dashboardRatio() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, String[]> result;
		result = new HashMap<String, String[]>();
		result.put("RatioRequestToMarchOnEachProcession", this.repositoryCustomisationSystem.requestToMarchOnEachProcession());
		//result.put("RatioRequestToMarchByStatus", (String[]) this.repositoryCustomisationSystem.findRatioRequestsToMarchByStatus());
		//result.put("CountPerArea", (String[]) this.repositoryCustomisationSystem.countPerArea());//area
		//result.put("MinMaxPerArea", (String[]) this.repositoryCustomisationSystem.minMaxPerArea());//area
		return result;
	}

	public Map<String, Map<String, Double>> dashboardRatio2() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Map<String, Double>> result;
		result = new HashMap<String, Map<String, Double>>();
		//		result.put("RatioRequestToMarchByStatus", this.repositoryCustomisationSystem.findRatioRequestsToMarchByStatus());
		//		result.put("CountPerArea", this.repositoryCustomisationSystem.countPerArea());//area
		//		result.put("MinMaxPerArea", this.repositoryCustomisationSystem.minMaxPerArea());//area

		final Collection<Object[]> ratiosRequestToMarch = this.repositoryCustomisationSystem.findRatioRequestsToMarchByStatus();

		Map<String, Double> ratiosRequest;
		ratiosRequest = new HashMap<String, Double>();

		for (final Object[] o : ratiosRequestToMarch) {
			final String s = String.valueOf(o[1]);
			final Double d = (Double) o[0];
			ratiosRequest.put(s, d);
		}

		result.put("RatioRequestToMarchByStatus", ratiosRequest);

		return result;
	}

	public Map<String, Double[]> marcadorNumericoArray() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double[]> result;
		result = new HashMap<String, Double[]>();
		/**/result.put("EnrolMemberMinMax", this.repositoryCustomisationSystem.enrolmentsMemberMinMax());//dos numeros (1)
		/**/result.put("NumberOfResultFinder", this.repositoryCustomisationSystem.numberOfResultsFinder());//los 4
		return result;
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

	public Map<String, Collection<Parade>> dashboardProcession() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Collection<Parade>> result;
		result = new HashMap<String, Collection<Parade>>();
		final Collection<Parade> processions = this.repositoryCustomisationSystem.findProcessionsInLessThan30Days();
		/**/result.put("ProcessionsInLessThan30Days", processions);
		return result;
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
