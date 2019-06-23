
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AoletService;
import services.AuditService;
import domain.Aolet;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping(value = {
	"/aolet/auditor", "/aolet"
})
public class AoletController extends BasicController {

	@Autowired
	private AoletService	service;

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/external", method = RequestMethod.GET)
	public ModelAndView listExternal(@RequestParam final int audit) {
		ModelAndView result;

		result = super.listModelAndView("aolets", "aolet/list", this.service.findAoletsByAuditFM(audit), "aolet/external.do?audit=" + audit);

		String lang;
		lang = super.getLanguageSystem();

		result.addObject("lang", lang == null ? "en" : lang);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int audit) {
		ModelAndView result;

		result = super.listModelAndView("aolets", "aolet/list", this.service.findAoletsByAuditDraftMode(audit), "aolet/auditor/list.do?audit=" + audit);

		String lang;
		lang = super.getLanguageSystem();
		result.addObject("lang", lang == null ? "en" : lang);

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int audit) {
		ModelAndView result;

		Auditor c;
		c = (Auditor) this.service.findActorByUA();

		Audit au;
		au = this.auditService.findOne(audit);

		if (c.getId() == au.getAuditor().getId())
			result = super.create(this.service.create(audit), "aolet/edit", "aolet/auditor/edit.do", "/aolet/auditor/list.do?audit=" + audit);
		else
			result = super.custom(new ModelAndView("403"));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {

		Aolet aolet;
		aolet = this.service.findOne(id);

		Auditor c;
		c = (Auditor) this.service.findActorByUA();

		Audit au;
		au = aolet.getAudit();

		Assert.isTrue(c.getId() == au.getAuditor().getId());

		String nameView;
		nameView = "aolet/edit";
		String requestURI;
		String requestCancel;
		requestCancel = "/aolet/auditor/list.do?audit=" + aolet.getAudit().getId();

		if (aolet.isFinalMode()) {
			requestURI = "aolet/auditor/show.do?id=" + id;
			return super.show(aolet, nameView, requestURI, requestCancel);
		} else {
			requestURI = "aolet/auditor/edit.do";
			return super.edit(aolet, nameView, requestURI, requestCancel);
		}
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Aolet aolet;
		aolet = this.service.findOne(id);

		String requestCancel;

		try {
			Auditor c;
			c = (Auditor) this.service.findActorByUA();
			requestCancel = "/aolet/list.do?audit=" + aolet.getAudit().getId();
		} catch (final Throwable oops) {
			requestCancel = "/aolet/external.do?audit=" + aolet.getAudit().getId();
		}

		return super.show(aolet, "aolet/edit", "aolet/show.do?id=" + id, requestCancel);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Aolet saved, final BindingResult binding) {

		ModelAndView result;

		int audit = 0;

		if (saved.getId() != 0)
			audit = this.service.findOne(saved.getId()).getAudit().getId();
		else
			audit = saved.getAudit().getId();

		result = super.save(saved, binding, "aolet.error", "aolet/edit", "aolet/auditor/save.do", "/aolet/auditor/list.do?audit=" + audit, "redirect:/aolet/auditor/list.do?audit=" + audit);

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		Aolet aolet;
		aolet = this.service.findOne(id);
		return super.delete(aolet, "aolet.error", "aolet/edit", "aolet/auditor/edit.do?id=" + aolet.getId(), "/aolet/auditor/list.do?audit=" + aolet.getAudit().getId(), "redirect:/aolet/auditor/list.do?audit=" + aolet.getAudit().getId());
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		Aolet aolet;
		aolet = (Aolet) e;

		aolet = this.service.reconstruct(aolet, binding);

		this.service.save(aolet);

		return new ModelAndView(nameResolver);
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		Aolet aolet;
		aolet = (Aolet) e;

		this.service.delete(aolet.getId());

		return new ModelAndView(nameResolver);
	}

}
