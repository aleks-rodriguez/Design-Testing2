
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.HistoryService;
import services.ParadeService;
import domain.Brotherhood;

@Controller
@RequestMapping(value = {
	"/history/brotherhood", "/history"
})
public class HistoryController extends AbstractController {

	@Autowired
	HistoryService	historyService;

	@Autowired
	ParadeService	paradeService;

	@Autowired
	ActorService	actorService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = null;
		UserAccount user;
		user = LoginService.getPrincipal();
		Brotherhood b;
		b = this.paradeService.findBrotherhoodByUser(user.getId());
		if (b.getHistory() == null) {
			result = this.custom(new ModelAndView("history/list"));
			result.addObject("show", false);
			result.addObject("inception", false);
		} else {
			result = this.custom(new ModelAndView("history/list"));
			result.addObject("inceptionRecord", b.getHistory().getInceptionRecord());
			result.addObject("legalRecord", b.getHistory().getLegalRecord());
			result.addObject("periodRecord", b.getHistory().getPeriodRecord());
			result.addObject("miscellaneousRecord", b.getHistory().getMiscellaneousRecord());
			result.addObject("linkRecord", b.getHistory().getLinkRecord());
			result.addObject("inception", true);
			result.addObject("show", false);
		}
		result.addObject("requestURI", "history/list.do");
		return result;
	}

	//List
	@RequestMapping(value = "/listHistory", method = RequestMethod.GET)
	public ModelAndView listHistory(@RequestParam final int idBrotherhood) {
		ModelAndView result = null;
		Brotherhood b;
		b = this.actorService.findOneBrotherhood(idBrotherhood);
		if (b.getHistory() == null) {
			result = this.custom(new ModelAndView("history/list"));
			result.addObject("inception", false);
			result.addObject("show", true);
		} else {
			result = this.custom(new ModelAndView("history/list"));
			result.addObject("inceptionRecord", b.getHistory().getInceptionRecord());
			result.addObject("legalRecord", b.getHistory().getLegalRecord());
			result.addObject("periodRecord", b.getHistory().getPeriodRecord());
			result.addObject("miscellaneousRecord", b.getHistory().getMiscellaneousRecord());
			result.addObject("linkRecord", b.getHistory().getLinkRecord());
			result.addObject("inception", true);
			result.addObject("show", true);
		}
		result.addObject("requestURI", "history/list.do");
		return result;
	}
}
