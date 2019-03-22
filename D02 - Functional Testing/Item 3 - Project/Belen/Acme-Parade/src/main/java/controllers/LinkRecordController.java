
package controllers;

import java.util.ArrayList;
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
import services.ActorService;
import services.LinkRecordService;
import services.ParadeService;
import domain.Brotherhood;
import domain.LinkRecord;

@Controller
@RequestMapping(value = {
	"/linkRecord/brotherhood", "/linkRecord"
})
public class LinkRecordController extends AbstractController {

	@Autowired
	private LinkRecordService	linkService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private ActorService		brotherhoodService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idLink) {
		ModelAndView result;
		LinkRecord l;
		l = this.linkService.createLinkRecord();
		result = this.createEditModelAndView(l);
		result.addObject("brotherhoodList", this.brotherhoodService.findAllBrotherhood());
		result.addObject("requestURI", "linkRecord/brotherhood/edit.do?idlink=" + idLink);
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(defaultValue = "0") final int idLink, LinkRecord l, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(l);
			result.addObject("brotherhoodList", this.brotherhoodService.findAllBrotherhood());
			result.addObject("requestURI", "linkRecord/brotherhood/edit.do?idlink=" + idLink);
		} else
			try {
				l = this.linkService.reconstruct(l, binding);
				this.linkService.save(l);
				result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
				result.addObject("inception", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(l, "linkRecord.commit.error");
			}
		return result;
	}

	//Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam final int idLink) {
		ModelAndView result;
		LinkRecord l;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		l = this.linkService.findOne(idLink);
		Assert.notNull(b.getHistory(), "You don't have access");
		Assert.isTrue(b.getHistory().getLinkRecord().contains(this.linkService.findOne(idLink)), "You don't have access to edit this link record");
		if (!(this.paradeService.findBrotherhoodByUser(user.getId()).getHistory().getLinkRecord().contains(this.linkService.findOne(idLink))))
			result = new ModelAndView("redirect:/history/brotherhood/list.do");
		else {
			result = this.createEditModelAndView(l);
			result.addObject("requestURI", "linkRecord/brotherhood/edit.do?idlink=" + idLink);
			result.addObject("brotherhoodList", this.brotherhoodService.findAllBrotherhood());
		}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idLink) {
		ModelAndView result = null;
		final LinkRecord l;
		l = this.linkService.findOne(idLink);
		try {
			this.linkService.delete(l.getId());
			result = this.custom(new ModelAndView("redirect:/history/brotherhood/list.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(this.linkService.findOne(idLink), "linkRecord.commit.error");
		}
		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idLink) {
		final ModelAndView result;
		final LinkRecord l;
		l = this.linkService.findOne(idLink);
		result = this.createEditModelAndView(l);
		result.addObject("requestURI", "linkRecord/show.do?idLink=" + l.getId());
		ArrayList<Brotherhood> brotherhoodByLinkRecord;
		brotherhoodByLinkRecord = new ArrayList<>();
		brotherhoodByLinkRecord.add(l.getBrotherhood());
		result.addObject("brotherhoodList", brotherhoodByLinkRecord);
		result.addObject("view", true);
		return result;
	}
	// Create edit model and view
	protected ModelAndView createEditModelAndView(final LinkRecord l) {
		ModelAndView model;
		model = this.createEditModelAndView(l, null);
		return model;
	}

	protected ModelAndView createEditModelAndView(final LinkRecord l, final String message) {
		ModelAndView result;

		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("id", String.valueOf(l.getId()));

		result = this.editFormsUrlId("linkRecord/brotherhood/edit.do", map, "/linkRecord/brotherhood/list.do", this.custom(new ModelAndView("linkRecord/edit")));
		result.addObject("linkRecord", l);
		result.addObject("message", message);
		return result;
	}
}
