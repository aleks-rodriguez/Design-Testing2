
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
import services.MiscellaneousDataService;
import domain.Curricula;
import domain.MiscellaneousData;

@Controller
@RequestMapping(value = "/miscData/rookie")
public class MiscellaneousDataController extends BasicController {

	@Autowired
	private CurriculaService			serviceCurricula;
	@Autowired
	private MiscellaneousDataService	service;

	private final String				nameView	= "miscellaneousData/edit";

	private int							curricula;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curricula) {
		Assert.isTrue(this.checkCurricula(curricula), "You don't have permission to do this");
		this.curricula = curricula;

		return super.create(this.service.create(), this.nameView, "miscData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Curricula curricula;
		curricula = this.service.findCurriculaByMiscellaneousDataId(id);
		Assert.isTrue(this.checkCurricula(curricula.getId()), "You don't have permission to do this");
		return super.edit(this.service.findOne(id), this.nameView, "miscData/rookie/edit.do?curricula=" + curricula.getId(), "/curricula/rookie/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Curricula curricula;
		curricula = this.service.findCurriculaByMiscellaneousDataId(id);
		return super.show(this.service.findOne(id), this.nameView, "miscData/rookie/edit.do?curricula=" + curricula.getId(), "/curricula/rookie/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int curricula, final MiscellaneousData miscellaneousData, final BindingResult binding) {
		Assert.isTrue(this.checkCurricula(curricula), "You don't have permission to do this");

		if (super.checkURL(miscellaneousData.getUrls(), true))
			return super.save(miscellaneousData, binding, "miscData.commit.error", this.nameView, "miscData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula, "redirect:/curricula/rookie/show.do?id=" + curricula);
		else
			return super.createAndEditModelAndView(miscellaneousData, "miscData.urls", this.nameView, "miscData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousData miscellaneous) {
		Curricula curricula;
		curricula = this.service.findCurriculaByMiscellaneousDataId(miscellaneous.getId());
		Assert.isTrue(this.checkCurricula(curricula.getId()), "You don't have permission to do this");
		return super.delete(miscellaneous, "miscData.commit.error", this.nameView, "miscData/rookie/edit.do", "/curricula/rookie/show.do?id = " + curricula.getId(), "redirect:/curricula/rookie/show.do?id=" + curricula.getId());
	}
	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		MiscellaneousData miscData;
		miscData = (MiscellaneousData) e;
		miscData = this.service.reconstruct(miscData, binding);

		if (miscData.getId() == 0)
			this.service.save(miscData, this.curricula);
		else {
			Curricula curricula;
			curricula = this.service.findCurriculaByMiscellaneousDataId(miscData.getId());
			this.service.save(miscData, curricula.getId());
		}

		ModelAndView result;
		result = new ModelAndView(nameResolver);

		return result;
	}
	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		MiscellaneousData misc;
		misc = (MiscellaneousData) e;
		this.service.delete(misc.getId());
		return new ModelAndView(nameResolver);
	}
	public boolean checkCurricula(final int curricula) {
		return this.serviceCurricula.findAllByRookie().contains(this.serviceCurricula.findOne(curricula));
	}
}
