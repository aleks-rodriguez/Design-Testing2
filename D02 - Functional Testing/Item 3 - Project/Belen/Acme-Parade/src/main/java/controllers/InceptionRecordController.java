
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
import services.InceptionRecordService;
import services.ParadeService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.InceptionRecord;

@Controller
@RequestMapping(value = {
	"/inceptionRecord/brotherhood", "/inceptionRecord"
})
public class InceptionRecordController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionService;

	@Autowired
	private ParadeService			paradeService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idInception) {
		ModelAndView result;
		InceptionRecord i;
		i = this.inceptionService.createInceptionRecord();
		result = this.createEditModelAndView(i);
		result.addObject("requestURI", "inceptionRecord/brotherhood/edit.do?idInception=" + idInception);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idInception, InceptionRecord i, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(i);
			result.addObject("requestURI", "inceptionRecord/brotherhood/edit.do?idInception=" + idInception);
		} else
			try {
				if (i.getPhotos().size() > 0 ? Utiles.checkURL(i.getPhotos()) : true) {
					i = this.inceptionService.reconstruct(i, binding);
					this.inceptionService.save(i);
					result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
					result.addObject("inception", true);
				} else
					result = this.createEditModelAndView(i, "inceptionRecord.urls");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(i, "inceptionRecord.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idInception) {
		ModelAndView result;
		InceptionRecord i;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		i = this.inceptionService.findOne(idInception);
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getInceptionRecord().getId() == this.inceptionService.findOne(idInception).getId(), "You don't have access to edit this inception record");
		if (!(b.getHistory().getInceptionRecord().equals(this.inceptionService.findOne(idInception))))
			result = new ModelAndView("redirect:/");
		else {
			result = this.createEditModelAndView(i);
			result.addObject("requestURI", "inceptionRecord/brotherhood/edit.do?idInception=" + idInception);
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idInception) {
		final ModelAndView result;
		final InceptionRecord i;
		i = this.inceptionService.findOne(idInception);
		result = this.createEditModelAndView(i);
		result.addObject("requestURI", "inceptionRecord/show.do?idInception=" + i.getId());
		result.addObject("view", true);
		return result;
	}

	// Create edit model and view
	protected ModelAndView createEditModelAndView(final InceptionRecord i) {
		ModelAndView model;
		model = this.createEditModelAndView(i, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final InceptionRecord i, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(i.getId()));

		result = this.editFormsUrlId("inceptionRecord/brotherhood/edit.do", map, "/inceptionRecord/brotherhood/list.do", this.custom(new ModelAndView("inceptionRecord/edit")));
		result.addObject("inceptionRecord", i);
		result.addObject("message", message);
		return result;
	}

}
