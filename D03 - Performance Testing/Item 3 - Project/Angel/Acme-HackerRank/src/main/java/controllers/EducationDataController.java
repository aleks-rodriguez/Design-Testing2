
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
import domain.Curricula;
import domain.DomainEntity;
import domain.EducationData;

@Controller
@RequestMapping(value = "/educationData/hacker")
public class EducationDataController extends BasicController {

	@Autowired
	private CurriculaService		serviceCurricula;

	@Autowired
	private EducationDataService	service;

	private final String			nameEntity	= "educationData";

	private final String			nameView	= "educationData/edit";

	private int						curricula;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curricula) {
		Assert.isTrue(this.checkCurricula(curricula));
		this.curricula = curricula;

		return super.create(this.service.create(), this.nameEntity, this.nameView, "educationData/hacker/edit.do?curricula=" + curricula, "/curricula/hacker/show.do?id=" + curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {

		Curricula curricula;
		curricula = this.service.findCurriculaByEducationDataId(id);
		Assert.isTrue(this.checkCurricula(curricula.getId()));
		return super.edit(this.service.findOne(id), this.nameEntity, this.nameView, "educationData/hacker/edit.do?curricula=" + curricula.getId(), "/curricula/hacker/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Curricula curricula;
		curricula = this.service.findCurriculaByEducationDataId(id);
		return super.show(this.service.findOne(id), this.nameEntity, this.nameView, "educationData/hacker/edit.do?curricula=" + curricula.getId(), "/curricula/hacker/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int curricula, final EducationData educationData, final BindingResult binding) {
		Assert.isTrue(this.checkCurricula(curricula));
		return super.save(educationData, binding, "educationData.commit.error", this.nameEntity, this.nameView, "educationData/hacker/edit.do?curricula=" + curricula, "/curricula/hacker/show.do?id=" + curricula, "redirect:/curricula/hacker/show.do?id="
			+ curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData educationData) {
		Curricula curricula;
		curricula = this.service.findCurriculaByEducationDataId(educationData.getId());
		Assert.isTrue(this.checkCurricula(curricula.getId()));
		return super.delete(educationData, "educationData.commit.error", this.nameEntity, this.nameView, "educationData/hacker/edit.do", "/curricula/hacker/show.do?id = " + curricula.getId(), "redirect:/curricula/hacker/show.do?id=" + curricula.getId());
	}
	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		EducationData educationData;
		educationData = (EducationData) e;
		educationData = this.service.reconstruct(educationData, binding);

		if (educationData.getId() == 0)
			this.service.save(educationData, this.curricula);
		else {
			Curricula curricula;
			curricula = this.service.findCurriculaByEducationDataId(educationData.getId());
			this.service.save(educationData, curricula.getId());
		}

		ModelAndView result;
		result = new ModelAndView(nameResolver);

		return result;
	}
	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {

		this.service.delete(e.getId());
		return new ModelAndView(nameResolver);
	}
	public boolean checkCurricula(final int curricula) {
		return this.serviceCurricula.findAllByHacker().contains(this.serviceCurricula.findOne(curricula));
	}
}
