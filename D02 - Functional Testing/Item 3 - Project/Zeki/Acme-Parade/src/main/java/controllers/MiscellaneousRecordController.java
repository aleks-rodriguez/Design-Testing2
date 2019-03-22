
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
import services.MiscellaneousRecordService;
import services.ParadeService;
import domain.Brotherhood;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping(value = {
	"/miscellaneousRecord/brotherhood", "/miscellaneousRecord"
})
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService	miscellaneousService;

	@Autowired
	private ParadeService				paradeService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idMiscellaneous) {
		ModelAndView result;
		MiscellaneousRecord p;
		p = this.miscellaneousService.createMiscellaneousRecord();
		result = this.createEditModelAndView(p);
		result.addObject("requestURI", "miscellaneousRecord/brotherhood/edit.do?idMiscellaneous=" + idMiscellaneous);
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idMiscellaneous, MiscellaneousRecord m, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(m);
			if (m.getId() != 0)
				result.addObject("requestURI", "miscellaneousRecord/brotherhood/edit.do?idMiscellaneous=" + idMiscellaneous);
			else
				result.addObject("requestURI", "miscellaneousRecord/brotherhood/edit.do");
		} else
			try {
				m = this.miscellaneousService.reconstruct(m, binding);
				this.miscellaneousService.save(m);
				result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
				result.addObject("inception", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "miscellaneousRecord.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idMiscellaneous) {
		ModelAndView result;
		MiscellaneousRecord m;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		m = this.miscellaneousService.findOne(idMiscellaneous);
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getMiscellaneousRecord().contains(this.miscellaneousService.findOne(idMiscellaneous)), "You don't have access to edit this miscellaneous record");
		if (!(this.paradeService.findBrotherhoodByUser(user.getId()).getHistory().getMiscellaneousRecord().contains(this.miscellaneousService.findOne(idMiscellaneous))))
			result = new ModelAndView("redirect:/history/brotherhood/list.do");
		else {
			result = this.createEditModelAndView(m);
			result.addObject("requestURI", "miscellaneousRecord/brotherhood/edit.do?idmiscellaneous=" + idMiscellaneous);
		}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idMiscellaneous) {
		ModelAndView result = null;
		final MiscellaneousRecord m;
		m = this.miscellaneousService.findOne(idMiscellaneous);
		try {
			this.miscellaneousService.delete(m.getId());
			result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.miscellaneousService.findOne(idMiscellaneous), "miscellaneousRecord.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idMiscellaneous) {
		final ModelAndView result;
		final MiscellaneousRecord m;
		m = this.miscellaneousService.findOne(idMiscellaneous);
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "miscellaneousRecord/show.do?idMiscellaneous=" + m.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord m) {
		ModelAndView model;
		model = this.createEditModelAndView(m, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord m, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(m.getId()));

		result = this.editFormsUrlId("miscellaneousRecord/brotherhood/edit.do", map, "/miscellaneousRecord/brotherhood/list.do", this.custom(new ModelAndView("miscellaneousRecord/edit")));
		result.addObject("miscellaneousRecord", m);
		result.addObject("message", message);
		return result;
	}
}
