
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
import services.PelfService;
import domain.Company;
import domain.Pelf;

@Controller
@RequestMapping(value = {
	"/pelf/company", "/pelf"
})
public class PelfController extends BasicController {

	@Autowired
	private PelfService		pelfService;
	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView listOwn(@RequestParam(defaultValue = "0") final int id) {
		Company c;
		ModelAndView result;
		c = (Company) this.pelfService.findActorByUserAccountId(LoginService.getPrincipal().getId());
		if (id == 0)
			result = super.listModelAndView("pelfs", "pelf/list", this.pelfService.findAllByCompanyId(c.getId()), "pelf/company/listOwn.do");
		else
			result = super.listModelAndView("pelfs", "pelf/list", this.pelfService.findAllByCompany(c.getId(), id), "pelf/company/listOwn.do?id=" + id);
		result.addObject("lang", this.getLanguageSystem());
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPublic(@RequestParam final int id) {
		ModelAndView result;
		result = super.listModelAndView("pelfs", "pelf/list", this.pelfService.findAllFinalByAuditId(id), "pelf/list.do?id=" + id);
		result.addObject("lang", this.getLanguageSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int id) {
		ModelAndView result;
		try {
			Assert.isTrue(this.pelfService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
			Assert.isTrue(LoginService.getPrincipal().getId() == this.auditService.findOne(id).getPosition().getCompany().getAccount().getId());
			result = this.create(this.pelfService.create(id), "pelf/edit", "pelf/company/edit.do", "pelf/company/listOwn.do");
		} catch (final IllegalArgumentException oops) {
			result = this.custom(new ModelAndView("403"));
		}
		return result;

	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView edit(final int id) {
		ModelAndView result;
		try {
			Assert.isTrue(this.pelfService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
			Pelf q;
			Company c;
			q = this.pelfService.findOne(id);
			c = (Company) this.pelfService.findActorByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(this.pelfService.findAllByCompanyId(c.getId()).contains(q));
			Assert.isTrue(LoginService.getPrincipal().getId() == q.getAudit().getPosition().getCompany().getAccount().getId());
			Assert.isTrue(!q.isFinalMode());
			result = super.edit(q, "pelf/edit", "pelf/company/edit.do", "pelf/company/listOwn.do?id=" + q.getAudit().getId());
		} catch (final IllegalArgumentException oops) {

			result = this.custom(new ModelAndView("403"));
		}
		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Pelf pelf, final BindingResult binding) {
		ModelAndView result;
		try {
			Assert.isTrue(this.pelfService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
			Company c;
			c = (Company) this.pelfService.findActorByUserAccountId(LoginService.getPrincipal().getId());
			if (pelf.getId() != 0) {
				Assert.isTrue(this.pelfService.findAllByCompanyId(c.getId()).contains(pelf));
				pelf.setTicker(this.pelfService.findOne(pelf.getId()).getTicker());
			}
			Assert.isTrue(LoginService.getPrincipal().getId() == pelf.getAudit().getPosition().getCompany().getAccount().getId());
			result = super.save(pelf, binding, "pelf.commit.error", "pelf/edit", "pelf/company/edit.do", "pelf/company/listOwn.do?id=" + pelf.getAudit().getId(), "redirect:listOwn.do?id=" + pelf.getAudit().getId());
		} catch (final IllegalArgumentException oops) {
			result = this.custom(new ModelAndView("403"));
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		try {
			Assert.isTrue(this.pelfService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
			Pelf q;
			q = this.pelfService.findOne(id);
			Company c;
			c = (Company) this.pelfService.findActorByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(this.pelfService.findAllByCompanyId(c.getId()).contains(q));
			Assert.isTrue(LoginService.getPrincipal().getId() == q.getAudit().getPosition().getCompany().getAccount().getId());
			result = super.show(q, "pelf/edit", "pelf/company/edit.do", "pelf/company/listOwn.do?id=" + q.getAudit().getId());
			result.addObject("view", true);
		} catch (final IllegalArgumentException oops) {
			result = this.custom(new ModelAndView("403"));
		}
		return result;
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result;
		Pelf q;
		q = this.pelfService.findOne(id);

		if (q == null)
			throw new IllegalArgumentException("This element does not exits");

		try {
			Assert.isTrue(!q.isFinalMode());
			Assert.isTrue(this.pelfService.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY));
			Company c;
			c = (Company) this.pelfService.findActorByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(this.pelfService.findAllByCompanyId(c.getId()).contains(q));
			Assert.isTrue(LoginService.getPrincipal().getId() == q.getAudit().getPosition().getCompany().getAccount().getId());
			return super.delete(q, "pelf.commit.error", "pelf/edit", "company/pelf/edit.do", "redirect:listOwn.do?id=" + q.getAudit().getId(), "redirect:listOwn.do?id=" + q.getAudit().getId());
		} catch (final IllegalArgumentException oops) {
			result = this.custom(new ModelAndView("403"));
		}
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Pelf pelf;
		pelf = (Pelf) e;
		pelf = this.pelfService.reconstruct(pelf, binding);
		this.pelfService.save(pelf);
		result = new ModelAndView(nameResolver);
		return result;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		Pelf pelf;
		pelf = (Pelf) e;
		this.pelfService.delete(pelf.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
