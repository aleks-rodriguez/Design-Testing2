
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProfileRepository;
import security.LoginService;
import domain.Actor;
import domain.Profile;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private ProfileRepository	profileRepository;

	@Autowired
	private Validator			validator;


	public Actor getActorByUser(final int id) {
		return this.profileRepository.findActorByUserAccountId(id);
	}

	public Profile findOne(final int id) {
		Actor a;
		a = this.profileRepository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Profile profile;
		profile = this.profileRepository.findOne(id);

		Assert.isTrue(a.getProfiles().contains(profile));

		return profile;
	}

	public Profile createProfile() {
		Profile result;
		result = new Profile();
		result.setNick("");
		result.setLink("");
		result.setSocialNetworkName("");
		return result;
	}

	public Profile save(final Profile p) {

		Actor a;
		a = this.profileRepository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Collection<Profile> profiles;
		profiles = a.getProfiles();

		Profile modify;

		if (p.getId() == 0)
			modify = this.profileRepository.save(p);
		else {
			Assert.isTrue(profiles.contains(p));
			modify = this.profileRepository.save(p);
		}

		if (!profiles.contains(p)) {
			profiles.add(modify);
			a.setProfiles(profiles);
		}

		return modify;
	}
	public void deleteProfile(final int id) {

		Actor a;
		a = this.profileRepository.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Profile profile;
		profile = this.profileRepository.findOne(id);

		Collection<Profile> pro;
		pro = a.getProfiles();

		Assert.isTrue(pro.contains(profile));

		if (pro.contains(profile)) {
			pro.remove(profile);
			a.setProfiles(pro);
			this.profileRepository.delete(profile);
		}

	}
	public Profile reconstruct(final Profile profile, final BindingResult binding) {
		Profile result;
		if (profile.getId() == 0)
			result = profile;
		else {
			result = this.profileRepository.findOne(profile.getId());
			result.setLink(profile.getLink());
			result.setNick(profile.getNick());
			result.setSocialNetworkName(profile.getSocialNetworkName());

		}
		this.validator.validate(result, binding);
		return result;
	}
}
