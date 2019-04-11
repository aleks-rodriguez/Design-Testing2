
package services;

import java.util.Collection;
import java.util.HashMap;
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
		a = this.repository.findActorByUserAccountId(id);
		a.getAccount().setEnabled(false);
	}

	public void unBanActor(final int id) {
		Assert.isTrue(super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Actor a;
		a = this.repository.findActorByUserAccountId(id);
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

		Map<String, String> spamWords;
		spamWords = new HashMap<String, String>();

		spamWords.put("en", c.getSpamwordsEnglish());
		spamWords.put("es", c.getSpamwordsSpanish());

		form.setSpamwords(spamWords);

		return form;
	}
}
