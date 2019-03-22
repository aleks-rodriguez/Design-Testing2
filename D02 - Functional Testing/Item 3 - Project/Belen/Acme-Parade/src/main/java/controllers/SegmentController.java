
package controllers;

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

import security.LoginService;
import services.ParadeService;
import services.SegmentService;
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
		try {
			Brotherhood b;
			b = this.serviceParade.findBrotherhoodByParade(parade);
			if (b.getAccount().equals(LoginService.getPrincipal()))
				result.addObject("mine", true);
			else
				result.addObject("mine", false);
		} catch (final IllegalArgumentException e) {
			result.addObject("mine", false);
		}
		final Collection<Segment> segments = this.serviceSegment.findAllSegmentsByParadeId(parade);
		result.addObject("segments", segments);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int parade) {
		ModelAndView result;
		result = this.createEditModelAndView(this.serviceSegment.create(this.serviceParade.findOne(parade)));
		result.addObject("view", true);
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
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "delete")
	public ModelAndView delete(final Segment segment) {
		ModelAndView result;
		try {
			result = this.custom(new ModelAndView("redirect:list.do?parade=" + segment.getParade().getId()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
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
