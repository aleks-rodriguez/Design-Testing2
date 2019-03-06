
package controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ProcessionService;
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
	private RequestService		reqService;
	@Autowired
	private ProcessionService	procService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int procId) {
		ModelAndView result;
		result = this.custom(new ModelAndView("request/list"));
		result.addObject("procesion", this.procService.findOne(procId));
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER)) {
			result.addObject("requests", this.reqService.findAllByProcessionAndMember(procId));
			result.addObject("requestURI", "request/member/list.do");
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
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final RequestForm requestF, final BindingResult binding) {
		ModelAndView result;
		Request request;
		int procId;
		procId = this.reqService.findByRequestId(requestF.getId()).getId();
		request = this.reqService.reconstruc(requestF, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				this.reqService.save(request, procId);
				result = this.custom(new ModelAndView("redirect:list.do?procId=" + procId));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request, "request.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request) {
		ModelAndView result;
		try {
			this.reqService.delete(request.getId());
			result = this.custom(new ModelAndView("redirect:welcome.do")); //Puede que este mal puesto
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.commit.error");
		}

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
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER)) {
			result.addObject("requestURI", "request/member/edit.do?id=" + request.getId());
			result.addObject("requestCancel", "request/member/list.do");
		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD)) {
			result.addObject("requestURI", "request/brotherhood/edit.do?=" + request.getId());
			result.addObject("requestCancel", "request/brotherhood/list.do");
		}
		result.addObject("disabled", this.disabled(request.getStatus()));
		return result;
	}

	private boolean disabled(final String status) {
		boolean disabled = false;
		switch (status) {
		case "APPROVED":
			disabled = true;
			break;
		case "PENDING":
			disabled = false;
			break;
		case "REJECTED":
			disabled = true;
			break;
		}
		return disabled;
	}
}
