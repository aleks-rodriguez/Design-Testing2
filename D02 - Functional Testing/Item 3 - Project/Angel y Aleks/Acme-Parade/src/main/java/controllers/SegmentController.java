
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ParadeService;
import services.SegmentService;
import utilities.Utiles;
import domain.Brotherhood;
import domain.Segment;

@Controller
@RequestMapping("/segment")
public class SegmentController extends AbstractController {

	@Autowired
	private SegmentService	serviceSegment;

	@Autowired
	private ParadeService	serviceParade;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int parade) {
		ModelAndView result;
		result = this.custom(new ModelAndView("segment/list"));
		Collection<Segment> segments;
		segments = new ArrayList<Segment>();

		try {
			Brotherhood b;
			b = this.serviceParade.findBrotherhoodByParade(parade);
			if (b.getAccount().equals(LoginService.getPrincipal())) {
				result.addObject("mine", true);
				segments = this.serviceSegment.findAllSegmentsByParadeId(parade);
			} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER)) {
				segments = this.serviceSegment.findAllSegmentsByParadeId(parade);
				result.addObject("mine", false);
			} else {
				segments = this.serviceSegment.findAllSegmentsByParadeIdClosePath(parade);
				result.addObject("mine", false);
			}
		} catch (final IllegalArgumentException e) {
			segments = this.serviceSegment.findAllSegmentsByParadeIdClosePath(parade);
			result.addObject("mine", false);
		}

		result.addObject("segments", segments);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int parade) {
		ModelAndView result;
		result = this.createEditModelAndView(this.serviceSegment.create(this.serviceParade.findOne(parade)));
		result.addObject("view", false);
		return result;
	}

	// Open and Close path

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView openPath(@RequestParam final int parade) {
		ModelAndView result;

		this.serviceSegment.openPath(parade);
		result = this.custom(new ModelAndView("redirect:list.do?parade=" + parade));

		return result;
	}
	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public ModelAndView closePath(@RequestParam final int parade) {
		ModelAndView result;

		result = this.createEditModelAndView(this.serviceSegment.closePath(parade));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int segment) {
		ModelAndView result;
		Segment s;
		s = this.serviceSegment.findOne(segment);
		result = this.createEditModelAndView(s);

		boolean view = false;

		try {
			Brotherhood b;
			b = this.serviceParade.findBrotherhoodByUser(LoginService.getPrincipal().getId());
			view = !b.getParades().contains(s.getParade());
		} catch (final IllegalArgumentException e) {
			view = true;
		}

		result.addObject("view", view);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Segment segment, final BindingResult binding) {
		ModelAndView result;

		try {
			segment = this.serviceSegment.reconstruct(segment, binding);
			this.serviceSegment.save(segment);

			result = this.custom(new ModelAndView("redirect:list.do?parade=" + segment.getParade().getId()));

		} catch (final ValidationException e) {
			result = this.createEditModelAndView(segment);
			result.addObject("view", false);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
			result.addObject("view", false);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Segment segment) {
		ModelAndView result;
		try {
			this.serviceSegment.delete(segment.getId());
			result = this.custom(new ModelAndView("redirect:list.do?parade=" + segment.getParade().getId()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
			result.addObject("view", true);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Segment segment) {
		ModelAndView result;
		result = this.createEditModelAndView(segment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Segment segment, final String message) {
		ModelAndView result;
		Map<String, String> requestParams;
		requestParams = new HashMap<String, String>();
		if (segment.getId() != 0)
			requestParams.put("id", String.valueOf(segment.getId()));

		result = this.editFormsUrlId("segment/edit.do", requestParams, "/segment/list.do", this.custom(new ModelAndView("segment/edit")));
		result.addObject("message", message);
		result.addObject("segment", segment);

		return result;
	}
}
