
package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ParadeService;
import services.PeriodRecordService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.PeriodRecord;

@Controller
@RequestMapping(value = {
	"/periodRecord/brotherhood", "/periodRecord"
})
public class PeriodRecordController extends AbstractController {

	@Autowired
	private PeriodRecordService	periodService;

	@Autowired
	private ParadeService		paradeService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idPeriod) {
		ModelAndView result;
		PeriodRecord p;
		p = this.periodService.createPeriodRecord();
		result = this.createEditModelAndView(p);
		result.addObject("requestURI", "periodRecord/brotherhood/edit.do?idPeriod=" + idPeriod);
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idPeriod, PeriodRecord p, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(p);
			if (p.getId() != 0)
				result.addObject("requestURI", "periodRecord/brotherhood/edit.do?idPeriod=" + idPeriod);
			else
				result.addObject("requestURI", "periodRecord/brotherhood/edit.do");
		} else
			try {
				if (p.getPhotos().size() > 0 ? Utiles.checkURL(p.getPhotos()) : true) {
					p = this.periodService.reconstruct(p, binding);
					this.periodService.save(p);
					result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
					result.addObject("inception", true);
				} else
					result = this.createEditModelAndView(p, "periodRecord.urls");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(p, "periodRecord.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idPeriod) {
		ModelAndView result;
		PeriodRecord p;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		p = this.periodService.findOne(idPeriod);
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getPeriodRecord().contains(this.periodService.findOne(idPeriod)), "You don't have access to edit this period record");
		if (!(this.paradeService.findBrotherhoodByUser(user.getId()).getHistory().getPeriodRecord().contains(this.periodService.findOne(idPeriod))))
			result = new ModelAndView("redirect:/history/brotherhood/list.do");
		else {
			result = this.createEditModelAndView(p);
			result.addObject("requestURI", "periodRecord/brotherhood/edit.do?idPeriod=" + idPeriod);
		}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idPeriod) {
		ModelAndView result = null;
		final PeriodRecord p;
		p = this.periodService.findOne(idPeriod);
		try {
			this.periodService.delete(p.getId());
			result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.periodService.findOne(idPeriod), "periodRecord.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idPeriod) {
		final ModelAndView result;
		final PeriodRecord p;
		p = this.periodService.findOne(idPeriod);
		result = this.createEditModelAndView(p);
		result.addObject("requestURI", "periodRecord/show.do?idPeriod=" + p.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final PeriodRecord p) {
		ModelAndView model;
		model = this.createEditModelAndView(p, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final PeriodRecord p, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(p.getId()));

		result = this.editFormsUrlId("periodRecord/brotherhood/edit.do", map, "/periodRecord/brotherhood/list.do", this.custom(new ModelAndView("periodRecord/edit")));
		result.addObject("periodRecord", p);
		result.addObject("message", message);
		return result;
	}
}
