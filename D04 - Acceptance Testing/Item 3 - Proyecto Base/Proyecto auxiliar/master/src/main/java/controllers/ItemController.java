
package controllers;

import java.util.ArrayList;
import java.util.Collection;

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
import services.ActorService;
import services.ItemService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping(value = {
	"/item", "/provider/item"
})
public class ItemController extends BasicController {

	@Autowired
	private ItemService		serviceItem;

	@Autowired
	private ActorService	serviceActor;


	@RequestMapping(value = "/showProvider", method = RequestMethod.GET)
	public ModelAndView showProviderByItem(@RequestParam final int item) {
		Provider p;
		ModelAndView result;
		p = this.serviceItem.showProviderByItem(item);
		result = super.show(this.serviceActor.map(this.serviceItem.showProviderByItem(item), Authority.PROVIDER), "actor/edit", "actor/edit.do", "/position/list.do");
		if (super.getLanguageSystem().equals("en") || super.getLanguageSystem() == null) {
			if (p.isSpammer())
				result.addObject("spammer", "Yes");
			else
				result.addObject("spammer", "No");
		} else if (p.isSpammer())
			result.addObject("spammer", "Si");
		else
			result.addObject("spammer", "No");
		result.addObject("view", true);
		return result;
	}

	@RequestMapping(value = "/listProviders", method = RequestMethod.GET)
	public ModelAndView listProviders() {
		return super.listModelAndView("providers", "provider/list", this.serviceItem.findAllProviders(), "item/listProvider.do");
	}

	@RequestMapping(value = "/listItems", method = RequestMethod.GET)
	public ModelAndView listItems() {
		return super.listModelAndView("items", "item/list", this.serviceItem.findAll(), "item/listItem.do");
	}

	@RequestMapping(value = "/listMyItems", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Item> MyItems;
		MyItems = new ArrayList<Item>();
		String requestURI = "";
		try {
			if (this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
				final Provider p = (Provider) this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId());
				MyItems = this.serviceItem.getItemsByProvider(p.getId());
				requestURI = "item/provider/list.do";
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			requestURI = "item/listItems.do";
		}
		ModelAndView result;
		result = super.listModelAndView("items", "item/list", MyItems, requestURI);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER));
		return super.create(this.serviceItem.create(), "item/edit", "item/edit.do", "/item/provider/list.do");
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result = null;
		Item i;
		i = this.serviceItem.findOne(id);
		try {
			if (this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
				Provider p;
				p = (Provider) this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId());
				if (i.getProvider().equals(p))
					result = super.edit(i, "item/edit", "item/provider/edit.do", "/item/provider/list.do");
				else
					throw new IllegalArgumentException();
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {

			result = super.show(i, "item/edit", "item/edit.do", "/item/list.do?provider=" + i.getProvider().getId());
		}
		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		// TODO Auto-generated method stub
		return null;
	}

}
