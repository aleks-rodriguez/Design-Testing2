
package controllers;

import java.util.Arrays;

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
import services.RequestService;
import utilities.Utiles;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping(value = {
	"request/brotherhood", "request/member"
})
public class RequestController extends AbstractController {

	@Autowired
	private RequestService	reqService;
	@Autowired
	private ParadeService	procService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int procId) {
		ModelAndView result;
		result = this.custom(new ModelAndView("request/list"));
		result.addObject("procesion", this.procService.findOne(procId));
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER)) {
			result.addObject("requests", this.reqService.findAllByProcessionAndMember(procId));
			result.addObject("requestURI", "request/member/list.do?procId=" + procId);
		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD)) {
			result.addObject("requests", this.reqService.findAllByProcession(procId));
			result.addObject("requestURI", "request/brotherhood/list.do");
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int procId) {
		ModelAndView result;
		result = this.createEditModelAndView(this.reqService.create());
		result.addObject("procId", procId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;
		Request request;
		request = this.reqService.findOne(id);
		result = this.custom(this.createEditModelAndView(request));
		if (id != 0)
			result.addObject("procId", this.reqService.findByRequestId(request.getId()).getId());
		result.addObject("numeros", this.reqService.optimPosition().toArray());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final RequestForm request, final BindingResult binding, @RequestParam final int procId) {
		ModelAndView result;
		result = null;
		Request aux = null;

		try {
			if (request.getMarchRow() == 0 && request.getMarchColumn() == 0) {
				request.setStatus("PENDING");
				result = this.createEditModelAndView(request, "request.formatofrowandcolumn");
			} else {
				aux = this.reqService.reconstruc(request, binding);
				this.reqService.save(aux, procId);
				result = this.custom(new ModelAndView("redirect:list.do?procId=" + procId));
			}
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(aux);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(aux, "request.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int id, @RequestParam(defaultValue = "false") final String view) {
		ModelAndView result;
		Request request;
		request = this.reqService.findOne(id);
		result = this.custom(this.createEditModelAndView(request));
		result.addObject("view", view);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request) {
		ModelAndView result;
		try {
			this.reqService.delete(request.getId());
			result = this.custom(new ModelAndView("redirect:welcome.do"));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestForm request) {
		return this.createEditModelAndView(request, null);
	}

	protected ModelAndView createEditModelAndView(final RequestForm request, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("request/edit"));
		result.addObject("request", request);
		result.addObject("message", message);
		result.addObject("status", Arrays.asList("APPROVED", "PENDING", "REJECTED"));
		result.addObject("statusMember", Arrays.asList("PENDING"));
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER))
			result.addObject("requestURI", "request/member/edit.do");
		//			result.addObject("requestCancel", "window.history.back()");
		else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD))
			result.addObject("requestURI", "request/brotherhood/edit.do");
		//			result.addObject("requestCancel", "window.history.back()");
		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request) {
		return this.createEditModelAndView(request, null);
	}

	protected ModelAndView createEditModelAndView(final Request request, final String message) {
		ModelAndView result;
		result = this.custom(new ModelAndView("request/edit"));
		result.addObject("request", request);
		result.addObject("message", message);
		result.addObject("status", Arrays.asList("APPROVED", "PENDING", "REJECTED"));
		result.addObject("statusMember", Arrays.asList("PENDING"));
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER))
			result.addObject("requestURI", "request/member/edit.do");
		//			result.addObject("requestCancel", "window.history.back()");
		else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD))
			result.addObject("requestURI", "request/brotherhood/edit.do");
		//			result.addObject("requestCancel", "window.history.back()");
		return result;
	}
}
