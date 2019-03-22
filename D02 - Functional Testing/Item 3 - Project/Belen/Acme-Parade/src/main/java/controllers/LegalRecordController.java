
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
import services.LegalRecordService;
import services.ParadeService;
import domain.Brotherhood;
import domain.LegalRecord;

@Controller
@RequestMapping(value = {
	"/legalRecord/brotherhood", "/legalRecord"
})
public class LegalRecordController extends AbstractController {

	@Autowired
	private LegalRecordService	legalService;

	@Autowired
	private ParadeService		paradeService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idLegal) {
		ModelAndView result;
		LegalRecord i;
		i = this.legalService.createLegalRecord();
		result = this.createEditModelAndView(i);
		result.addObject("requestURI", "legalRecord/brotherhood/edit.do?idLegal=" + idLegal);
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idLegal, LegalRecord l, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(l);
			result.addObject("requestURI", "legalRecord/brotherhood/edit.do?idLegal=" + idLegal);
		} else
			try {
				l = this.legalService.reconstruct(l, binding);
				this.legalService.save(l);
				result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
				result.addObject("inception", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(l, "legalRecord.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idLegal) {
		ModelAndView result;
		LegalRecord l;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		l = this.legalService.findOne(idLegal);
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getLegalRecord().contains(this.legalService.findOne(idLegal)), "You don't have access to edit this legal record");
		if (!(this.paradeService.findBrotherhoodByUser(user.getId()).getHistory().getLegalRecord().contains(this.legalService.findOne(idLegal))))
			result = new ModelAndView("redirect:/");
		else {
			result = this.createEditModelAndView(l);
			result.addObject("requestURI", "legalRecord/brotherhood/edit.do?idLegal=" + idLegal);
		}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idLegal) {
		ModelAndView result = null;
		final LegalRecord l;
		l = this.legalService.findOne(idLegal);
		try {
			this.legalService.delete(l.getId());
			result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.legalService.findOne(idLegal), "legalRecord.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idLegal) {
		final ModelAndView result;
		final LegalRecord l;
		l = this.legalService.findOne(idLegal);
		result = this.createEditModelAndView(l);
		result.addObject("requestURI", "legalRecord/show.do?idLegal=" + l.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final LegalRecord l) {
		ModelAndView model;
		model = this.createEditModelAndView(l, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final LegalRecord l, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(l.getId()));

		result = this.editFormsUrlId("legalRecord/brotherhood/edit.do", map, "/legalRecord/brotherhood/list.do", this.custom(new ModelAndView("legalRecord/edit")));
		result.addObject("legalRecord", l);
		result.addObject("message", message);
		return result;
	}
}
