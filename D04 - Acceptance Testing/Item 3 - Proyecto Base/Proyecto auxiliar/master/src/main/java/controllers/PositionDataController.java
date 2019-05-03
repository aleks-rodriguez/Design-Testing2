
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
import services.PositionDataService;
import domain.Curricula;
import domain.PositionData;

@Controller
@RequestMapping(value = "/posData/rookie")
public class PositionDataController extends BasicController {

	@Autowired
	private CurriculaService	serviceCurricula;

	@Autowired
	private PositionDataService	service;

	private final String		nameView	= "positionData/edit";

	private int					curricula;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curricula) {
		Assert.isTrue(this.checkCurricula(curricula), "You don't have permission to do this");
		this.curricula = curricula;

		return super.create(this.service.create(), this.nameView, "posData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Curricula curricula;
		curricula = this.service.findCurriculaByPositionDataId(id);
		Assert.isTrue(this.checkCurricula(curricula.getId()), "You don't have permission to do this");
		return super.edit(this.service.findOne(id), this.nameView, "posData/rookie/edit.do?curricula=" + curricula.getId(), "/curricula/rookie/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Curricula curricula;
		curricula = this.service.findCurriculaByPositionDataId(id);

		return super.show(this.service.findOne(id), this.nameView, "posData/rookie/edit.do?curricula=" + curricula.getId(), "/curricula/rookie/show.do?id=" + curricula.getId());
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int curricula, final PositionData positionData, final BindingResult binding) {
		Assert.isTrue(this.checkCurricula(curricula), "You don't have permission to do this");
		if (this.service.checkDates(positionData.getStartDate(), positionData.getEndDate()))
			return super.save(positionData, binding, "posData.commit.error", this.nameView, "posData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula, "redirect:/curricula/rookie/show.do?id=" + curricula);
		else
			return this.createAndEditModelAndView(positionData, "bad.dates", this.nameView, "posData/rookie/edit.do?curricula=" + curricula, "/curricula/rookie/show.do?id=" + curricula);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionData positionData) {
		Curricula curricula;
		curricula = this.service.findCurriculaByPositionDataId(positionData.getId());
		Assert.isTrue(this.checkCurricula(curricula.getId()), "You don't have permission to do this");
		return super.delete(positionData, "posData.commit.error", this.nameView, "posData/rookie/edit.do", "/curricula/rookie/show.do?id = " + curricula.getId(), "redirect:/curricula/rookie/show.do?id=" + curricula.getId());
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		PositionData positionData;
		positionData = (PositionData) e;
		positionData = this.service.reconstruct(positionData, binding);

		if (positionData.getId() == 0)
			this.service.save(positionData, this.curricula);
		else {
			Curricula curricula;
			curricula = this.service.findCurriculaByPositionDataId(positionData.getId());
			this.service.save(positionData, curricula.getId());
		}

		ModelAndView result;
		result = new ModelAndView(nameResolver);

		return result;
	}
	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		PositionData pos;
		pos = (PositionData) e;
		this.service.delete(pos.getId());
		return new ModelAndView(nameResolver);
	}
	public boolean checkCurricula(final int curricula) {
		return this.serviceCurricula.findAllByRookie().contains(this.serviceCurricula.findOne(curricula));
	}
}
