
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ProfileService;
import domain.DomainEntity;
import domain.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BasicController {

	@Autowired
	private ProfileService	service;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return super.listModelAndView("profiles", "profile/list", this.service.getActorByUser(LoginService.getPrincipal().getId()).getProfiles(), "profile/list.do");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return super.create(this.service.createProfile(), "profile", "profile/edit", "profile/edit.do", "/profile/list.do");
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		result = super.show(this.service.findOne(id), "profile", "profile/edit", "profile/edit.do", "profile/list.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		return super.edit(this.service.findOne(id), "profile", "profile/edit", "profile/edit.do", "/profile/list.do");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEntity(final Profile profile, final BindingResult binding) {
		ModelAndView result;
		if (profile.getId() != 0)
			Assert.isTrue(this.service.getActorByUser(LoginService.getPrincipal().getId()).getProfiles().contains(profile), "You don't have permission to do this");
		result = super.save(profile, binding, "profile.commit.error", "profile", "profile/edit", "profile/edit.do", "/profile/list.do", "redirect:list.do");
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteEntity(@RequestParam final int id) {
		ModelAndView result;
		Assert.isTrue(this.service.getActorByUser(LoginService.getPrincipal().getId()).getProfiles().contains(this.service.findOne(id)), "You don't have permission to do this");
		result = super.delete(this.service.findOne(id), "profile.commit.error", "profile", "profile/edit", "profile/edit.do", "/profile/list.do", "redirect:list.do");
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Profile profile;
		profile = (Profile) e;
		profile = this.service.reconstruct(profile, binding);
		this.service.save(profile);
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		Profile profile;
		profile = (Profile) e;
		this.service.deleteProfile(profile.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}
}
