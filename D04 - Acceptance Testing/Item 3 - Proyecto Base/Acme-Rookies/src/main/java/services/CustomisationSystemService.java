
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomisationSystemRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.CustomisationSystem;
import forms.CustomForm;

@Service
@Transactional
public class CustomisationSystemService extends AbstractService {

	@Autowired
	private CustomisationSystemRepository	repository;
	@Autowired
	private Validator						validator;


	public void flagSpam(final int idActor) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActorByUserAccountId(idActor);
		a.setSpammer(true);
	}
	public CustomisationSystem findUnique() {
		return this.repository.findAll().get(0);
	}
	public Collection<Actor> findAllNoEnabledActors() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repository.findAllNoEnabledActors();
	}

	public CustomisationSystem save(final CustomForm custom) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		CustomisationSystem aux;
		aux = this.fromCustomObjetFormToDomain(custom);
		aux.setId(this.findUnique().getId());
		aux.setVersion(this.findUnique().getVersion());
		return this.repository.save(aux);
	}
	public void banActor(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActor(id);
		a.getAccount().setEnabled(false);
	}

	public void unBanActor(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActor(id);
		a.getAccount().setEnabled(true);
	}

	public CustomForm reconstruct(final CustomForm c, final BindingResult binding) {

		this.validator.validate(c, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return c;
	}
	public CustomForm fromCustomisationSystemToObjetForm(final CustomisationSystem c) {
		CustomForm form;
		form = new CustomForm();

		form.setBanner(c.getBanner());
		form.setHoursFinder(c.getHoursFinder());
		form.setMessage(c.getMessage());
		form.setPhonePrefix(c.getPhonePrefix());
		form.setResultFinder(c.getResultFinder());
		form.setSystemName(c.getSystemName());
		form.setSpamwordsEnglish(c.getSpamwords().get("en"));
		form.setSpamwordsSpanish(c.getSpamwords().get("es"));
		form.setVat(c.getVat());
		form.setFlat_rate(c.getFlat_rate());

		return form;
	}
	public CustomisationSystem fromCustomObjetFormToDomain(final CustomForm c) {
		CustomisationSystem form;
		form = new CustomisationSystem();

		form.setBanner(c.getBanner());
		form.setHoursFinder(c.getHoursFinder());
		form.setMessage(c.getMessage());
		form.setPhonePrefix(c.getPhonePrefix());
		form.setResultFinder(c.getResultFinder());
		form.setSystemName(c.getSystemName());
		form.setVat(c.getVat());
		form.setFlat_rate(c.getFlat_rate());
		Map<String, String> spamWords;
		spamWords = new HashMap<String, String>();

		spamWords.put("en", c.getSpamwordsEnglish());
		spamWords.put("es", c.getSpamwordsSpanish());

		form.setSpamwords(spamWords);

		return form;
	}

	public Map<String, Double> marcadorNumerico() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double> result;
		result = new HashMap<String, Double>();
		if (this.repository.emptyNonEmptyFinders() == null || this.repository.averageSalaryMaxAverageAuditScore() == null) {
			result.put("EmptyVsNotEmpty", 0.0);
			result.put("averageSalaryMaxAverageAuditScore", 0.0);
		} else {
			result.put("EmptyVsNotEmpty", this.repository.emptyNonEmptyFinders());
			result.put("averageSalaryMaxAverageAuditScore", this.calculateAverage(this.repository.averageSalaryMaxAverageAuditScore()));
		}
		return result;
	}

	public Map<String, Double[]> marcadorNumericoArray() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, Double[]> result;
		result = new HashMap<String, Double[]>();
		result.put("NumberOfResultFinder", this.repository.numberOfResultsFinder());
		result.put("MinMaxAVGDttvPositionPerCompany", this.repository.minMaxAvgDttvOfPositionPerCompany());
		result.put("MinMaxAvgDttvOfApplicationPerRookie", this.repository.minMaxAvgDttvOfApplicationPerRookie());
		result.put("MinMaxAvgDttvOfSalary", this.repository.minMaxAvgDttvOfSalaries());
		result.put("MinMaxAvgSttdvOfCurriculaPerRookie", this.repository.minMaxAvgDttvOfCurriculaPerRookie());
		result.put("MinMaxAvgDttvOfAuditPerPosition", this.repository.minMaxAvgDttvOfAuditPerPosition());
		result.put("MinMaxAvgDttvOfAuditPerCompany", this.repository.minMaxAvgDttvOfAuditPerCompany());
		result.put("MinMaxAvgDttvOfItemPerProvider", this.repository.minMaxAvgDttvOfItemPerProvider());
		result.put("MinMaxAvgDttvOfSponsorshipsPerProvider", this.repository.minMaxAvgDttvOfSponsorshipsPerProvider());
		result.put("MinMaxAvgDttvOfSponsorshipsPerPosition", this.repository.minMaxAvgDttvOfSponsorshipsPerPosition());
		return result;
	}

	public Map<String, List<String>> CompanyRookies() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, List<String>> result;
		result = new HashMap<String, List<String>>();
		result.put("CompanyMorePositions", this.repository.findCompanyMorePositions());
		result.put("RookiesMoreApplication", this.repository.findRookiesMoreApplications());
		result.put("HighestCompaniesAuditScore", this.repository.highestCompaniesAuditScore());
		if (this.repository.top5ProviderPerMaxItems().size() == 0)
			result.put("Top5ProviderPerMaxItems", new ArrayList<String>());
		else if (this.repository.top5ProviderPerMaxItems().size() == 1)
			result.put("Top5ProviderPerMaxItems", this.repository.top5ProviderPerMaxItems().subList(0, 0));
		else if (this.repository.top5ProviderPerMaxItems().size() == 2)
			result.put("Top5ProviderPerMaxItems", this.repository.top5ProviderPerMaxItems().subList(0, 1));
		else if (this.repository.top5ProviderPerMaxItems().size() == 3)
			result.put("Top5ProviderPerMaxItems", this.repository.top5ProviderPerMaxItems().subList(0, 2));
		else if (this.repository.top5ProviderPerMaxItems().size() == 4)
			result.put("Top5ProviderPerMaxItems", this.repository.top5ProviderPerMaxItems().subList(0, 3));
		else if (this.repository.top5ProviderPerMaxItems().size() >= 5)
			result.put("Top5ProviderPerMaxItems", this.repository.top5ProviderPerMaxItems().subList(0, 4));

		result.put("ProvidersSponsorshipMoreThan10PerCent", this.repository.providersSponsorshipMoreThan10PerCent());
		return result;
	}
	public Map<String, String> BestWorstPositionSalary() {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Map<String, String> result;
		result = new HashMap<String, String>();

		String worst;
		String best;

		Collection<String> worsts;
		worsts = this.repository.findTheWorstPositionSalary();

		Collection<String> bests;
		bests = this.repository.findTheBestPositionSalary();

		if (bests.isEmpty())
			worst = " ";
		else
			worst = (String) worsts.toArray()[0];

		if (bests.isEmpty())
			best = " ";
		else
			best = (String) bests.toArray()[0];

		result.put("BestPosition", best);
		result.put("WorstPosition", worst);

		return result;
	}

	public Double calculateAverage(final List<Double> values) {
		Double sum = 0.0;
		Double res = 0.0;
		for (final Double val : values)
			sum = sum + val;
		res = sum / values.size();
		return res;
	}
}
