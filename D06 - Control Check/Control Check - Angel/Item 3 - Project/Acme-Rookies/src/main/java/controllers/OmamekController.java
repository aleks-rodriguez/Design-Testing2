
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.OmamekService;
import domain.Audit;
import domain.Company;
import domain.Omamek;

@Controller
@RequestMapping(value = {
	"/omamek/company", "/omamek"
})
public class OmamekController extends BasicController {

	@Autowired
	private OmamekService	service;

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/external", method = RequestMethod.GET)
	public ModelAndView listExternal(@RequestParam final int audit) {
		ModelAndView result;

		result = super.listModelAndView("omameks", "omamek/list", this.service.findOmameksByAuditFM(audit), "omamek/external.do?audit=" + audit);

		String lang;
		lang = super.getLanguageSystem();

		result.addObject("lang", lang == null ? "en" : lang);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int audit) {
		ModelAndView result;

		result = super.listModelAndView("omameks", "omamek/list", this.service.findOmameksByAuditDraftMode(audit), "omamek/company/list.do?audit=" + audit);

		String lang;
		lang = super.getLanguageSystem();
		result.addObject("lang", lang == null ? "en" : lang);

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int audit) {
		ModelAndView result;

		Company c;
		c = (Company) this.service.findActorByUA();

		Audit au;
		au = this.auditService.findOne(audit);

		if (c.getId() == au.getPosition().getCompany().getId())
			result = super.create(this.service.create(audit), "omamek/edit", "omamek/company/edit.do", "/omamek/company/list.do?audit=" + audit);
		else
			result = super.custom(new ModelAndView("403"));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {

		Omamek omamek;
		omamek = this.service.findOne(id);

		Company c;
		c = (Company) this.service.findActorByUA();

		Audit au;
		au = omamek.getAudit();

		Assert.isTrue(c.getId() == au.getPosition().getCompany().getId());

		String nameView;
		nameView = "omamek/edit";
		String requestURI;
		String requestCancel;
		requestCancel = "/omamek/company/list.do?audit=" + omamek.getAudit().getId();

		if (omamek.isFinalMode()) {
			requestURI = "omamek/company/show.do?id=" + id;
			return super.show(omamek, nameView, requestURI, requestCancel);
		} else {
			requestURI = "omamek/company/edit.do?id=" + id;
			return super.edit(omamek, nameView, requestURI, requestCancel);
		}
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Omamek omamek;
		omamek = this.service.findOne(id);

		String requestCancel;

		try {
			Company c;
			c = (Company) this.service.findActorByUA();
			requestCancel = "/omamek/list.do?audit=" + omamek.getAudit().getId();
		} catch (final Throwable oops) {
			requestCancel = "/omamek/external.do?audit=" + omamek.getAudit().getId();
		}
		if (omamek.isFinalMode())
			return super.show(omamek, "omamek/edit", "omamek/show.do?id=" + id, requestCancel);
		else
			return super.custom(new ModelAndView("403"));
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Omamek saved, final BindingResult binding) {

		ModelAndView result;

		int audit = 0;

		if (saved.getId() != 0)
			audit = this.service.findOne(saved.getId()).getAudit().getId();
		else
			audit = saved.getAudit().getId();

		result = super.save(saved, binding, "omamek.error", "omamek/edit", "omamek/company/save.do", "/omamek/company/list.do?audit=" + audit, "redirect:/omamek/company/list.do?audit=" + audit);

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		Omamek omamek;
		omamek = this.service.findOne(id);
		return super.delete(omamek, "omamek.error", "omamek/edit", "omamek/company/edit.do?id=" + omamek.getId(), "/omamek/company/list.do?audit=" + omamek.getAudit().getId(), "redirect:/omamek/company/list.do?audit=" + omamek.getAudit().getId());
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {

		Omamek omamek;
		omamek = (Omamek) e;

		omamek = this.service.reconstruct(omamek, binding);

		this.service.save(omamek);

		return new ModelAndView(nameResolver);
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		Omamek omamek;
		omamek = (Omamek) e;

		this.service.delete(omamek.getId());

		return new ModelAndView(nameResolver);
	}

}
