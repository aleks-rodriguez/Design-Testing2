
package controllers;

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
		result = super.show(this.serviceActor.map(this.serviceItem.showProviderByItem(item), Authority.PROVIDER), "actor/edit", "actor/edit.do", "/item/listMyItems.do");
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
		result.addObject("authority", Authority.PROVIDER);
		return result;
	}

	@RequestMapping(value = "/listProviders", method = RequestMethod.GET)
	public ModelAndView listProviders() {
		return super.listModelAndView("providers", "provider/list", this.serviceItem.findAllProviders(), "item/listProvider.do");
	}

	@RequestMapping(value = "/listItems", method = RequestMethod.GET)
	public ModelAndView listItems() {
		final ModelAndView result = super.listModelAndView("items", "item/list", this.serviceItem.findAll(), "item/listItem.do");
		return result;
	}

	@RequestMapping(value = "/listMyItems", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int provider) {
		return super.listModelAndView("items", "item/list", this.serviceItem.getItemsByProvider(provider), "item/listMyItems");

	}
	@RequestMapping(value = "/listPersonalItems", method = RequestMethod.GET)
	public ModelAndView listMyItems() {
		final ModelAndView result = super.listModelAndView("items", "item/list", this.serviceItem.getItemsByProvider(this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId()).getId()), "item/listPersonalItems.do");
		//result.addObject("all", true);
		result.addObject("provider", true);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER));
		final ModelAndView result = super.create(this.serviceItem.create(), "item/edit", "item/edit.do.do", "");
		result.addObject("view", false);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int item) {
		ModelAndView result = null;
		Item i;
		i = this.serviceItem.findOne(item);
		try {
			if (this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
				Provider p;
				p = (Provider) this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId());
				if (i.getProvider().equals(p))
					result = super.show(i, "item/edit", "item/edit.do", "item/listPersonalItems.do");
				else
					throw new IllegalArgumentException();
			} else
				throw new IllegalArgumentException();
		} catch (final IllegalArgumentException e) {
			result = super.show(i, "item/edit", "item/edit.do", "/item/listMyItems.do?provider=" + i.getProvider().getId());
			result.addObject("view", true);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteEntity(@RequestParam final int id) {
		Provider p;
		p = (Provider) this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId());

		Item i;
		i = this.serviceItem.findOne(id);

		Collection<Item> col;
		col = this.serviceItem.getItemsByProvider(p.getId());

		Assert.isTrue(col.contains(i), "You don't have permission to do this");
		ModelAndView result;

		result = super.delete(this.serviceItem.findOne(id), "item.commit.error", "item/edit", "item/edit.do", "redirect:listPersonalItems.do", "redirect:listPersonalItems.do");
		result.addObject("provider", true);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEntity(final Item item, final BindingResult binding) {
		ModelAndView result = null;
		if (super.checkURL(item.getUrls(), true) && super.checkURL(item.getPictures(), true))
			result = super.save(item, binding, "item.commit.error", "item/edit", "item/edit.do", "item/listPersonalItems.do", "redirect:listPersonalItems.do");
		else
			result = super.createAndEditModelAndView(item, "bad.url", "item/edit", "item/edit.do", "item/listPersonalItems.do");
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		Provider p;
		p = (Provider) this.serviceItem.findByUserAccount(LoginService.getPrincipal().getId());

		Item item;
		item = this.serviceItem.findOne(id);

		Collection<Item> col;
		col = this.serviceItem.getItemsByProvider(p.getId());

		Assert.isTrue(col.contains(item), "You don't have permission to do this");
		String requestCancel = "";

		if (this.serviceItem.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER))
			requestCancel = "item/listPersonalItems.do";

		ModelAndView result;
		result = super.edit(this.serviceItem.findOne(id), "item/edit", "item/edit.do", requestCancel);

		return result;
	}

	@Override
	public <T> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		ModelAndView result;
		Item item;
		item = (Item) e;
		item = this.serviceItem.reconstruct(item, binding);
		this.serviceItem.save(item);
		result = new ModelAndView(nameResolver);
		return result;

	}

	@Override
	public <T> ModelAndView deleteAction(final T e, final String nameResolver) {
		ModelAndView result;
		Item item;
		item = (Item) e;
		this.serviceItem.delete(item.getId());
		result = new ModelAndView(nameResolver);
		return result;
	}

}
