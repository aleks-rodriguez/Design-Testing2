
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.AuditService;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping(value = {
	"/audit", "/audit/auditor"
})
public class AuditController extends BasicController {

	@Autowired
	private AuditService	service;

	private final String	audits	= "audits";
	private final String	edit	= "audit/edit";


	@RequestMapping(value = "/listPosition", method = RequestMethod.GET)
	public ModelAndView listMyPositions() {
		ModelAndView result;
		Auditor auditor;
		auditor = (Auditor) this.service.findActorByUserAccount(LoginService.getPrincipal().getId());

		result = super.listModelAndView("positions", "position/list", this.service.findPositionsByAuditor(auditor.getId()), "position/list.do");

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int position) {
		ModelAndView result = null;

		try {
			if (this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR))
				result = super.listModelAndView(this.audits, "audit/list", this.service.findAuditsByPosition(position), "audit/auditor/list.do?position=" + position);
			else
				throw new IllegalArgumentException();
		} catch (final Throwable oops) {
			result = super.listModelAndView(this.audits, "audit/list", this.service.findAuditsByPositionPublic(position), "audit/auditor/list.do?position=" + position);
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int position) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));
		return super.create(this.service.create(position), this.edit, "audit/auditor/edit.do", "/audit/auditor/list.do?position=" + position);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));
		Assert.isTrue(this.service.checkAudit(id), "This audit is not yours | Esta auditoria no es suya");
		Audit audit;
		audit = this.service.findOne(id);
		Assert.isTrue(!audit.isFinalMode(), "This audit is in FinalMode | Esta auditoria no se puede modificar");
		return super.edit(audit, this.edit, "audit/edit.do", "/audit/auditor/list.do?position=" + audit.getPosition().getId());
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		Audit audit;
		audit = this.service.findOne(id);
		return super.show(audit, this.edit, "audit/edit.do", "/audit/auditor/list.do?position=" + audit.getPosition().getId());
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Audit audit, final BindingResult binding) {
		if (audit.getId() != 0)
			Assert.isTrue(this.service.checkAudit(audit.getId()), "This audit is not yours | Esta auditoria no es suya");

		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));

		return super.save(audit, binding, "audit.error", "audit/edit", "audit/auditor/edit.do", "/audit/auditor/list.do?position=" + audit.getPosition().getId(), "redirect:/audit/auditor/list.do?position=" + audit.getPosition().getId());
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit) {
		Assert.isTrue(this.service.checkAudit(audit.getId()), "This audit is not yours | Esta auditoria no es suya");
		Assert.isTrue(this.service.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR));
		return super.delete(audit, "audit.error", this.edit, "audit/auditor/edit.do", "/audit/auditor/list.do?position=" + audit.getPosition().getId(), "redirect:/audit/auditor/list.do?position=" + audit.getPosition().getId());
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		Audit audit;
		audit = (Audit) e;
		audit = this.service.reconstruct(audit, binding);
		this.service.save(audit);
		ModelAndView result;
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		Audit audit;
		audit = (Audit) e;
		this.service.delete(audit.getId());
		ModelAndView result;
		result = new ModelAndView(nameResolver);
		return result;
	}

}
