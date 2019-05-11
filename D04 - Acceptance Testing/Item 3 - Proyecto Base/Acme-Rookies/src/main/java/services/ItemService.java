
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService extends AbstractService {

	@Autowired
	private ItemRepository	repositoryItem;

	@Autowired
	private Validator		validator;


	public Provider showProviderByItem(final int id) {
		return this.repositoryItem.showProviderByItem(id);
	}

	public Collection<Provider> findAllProviders() {
		return this.repositoryItem.findAllProviders();
	}

	public Actor findByUserAccount(final int id) {
		return this.repositoryItem.findActorByUserAccountId(id);
	}

	public Collection<Item> findAll() {
		return this.repositoryItem.findAll();
	}

	public Collection<Item> getItemsByProvider(final int id) {
		return this.repositoryItem.getItemsByProvider(id);
	}

	public Item findOne(final int id) {
		return this.repositoryItem.findOne(id);
	}

	public Item create() {

		super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER);

		Item item;
		item = new Item();

		item.setName("");
		item.setDescription("");
		item.setPictures(new ArrayList<String>());
		item.setUrls(new ArrayList<String>());

		return item;
	}
	public Item save(final Item id) {
		Item saved = null;

		Provider p;
		p = (Provider) this.repositoryItem.findActorByUserAccountId(LoginService.getPrincipal().getId());

		if (id.getId() == 0)
			id.setProvider(p);
		else
			Assert.isTrue(id.getProvider().equals(p));

		saved = this.repositoryItem.saveAndFlush(id);

		return saved;
	}

	public void delete(final int id) {

		Provider p;
		p = (Provider) this.repositoryItem.findActorByUserAccountId(LoginService.getPrincipal().getId());

		Item i;
		i = this.repositoryItem.findOne(id);

		Assert.isTrue(i.getProvider().equals(p), "You don't have permission to do this");

		this.repositoryItem.delete(id);

	}

	public Item reconstruct(final Item item, final BindingResult binding) {
		Item result;
		if (item.getId() == 0) {

			result = item;
			result.setProvider((Provider) this.repositoryItem.findActorByUserAccountId(LoginService.getPrincipal().getId()));
		} else {
			result = this.repositoryItem.findOne(item.getId());
			result.setName(item.getName());
			result.setDescription(item.getDescription());
			result.setPictures(item.getPictures());
			result.setUrls(item.getUrls());
		}
		this.checkCollection("urls", result.getUrls(), binding);
		this.checkCollection("pictures", result.getPictures(), binding);
		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public void checkCollection(final String attribute, final Collection<String> col, final BindingResult binding) {

		if (col.isEmpty())
			binding.rejectValue(attribute, "item.notblank");

	}

	public void flush() {
		this.repositoryItem.flush();
	}

	public void delete(final Collection<Item> col) {
		this.repositoryItem.delete(col);
	}
}
