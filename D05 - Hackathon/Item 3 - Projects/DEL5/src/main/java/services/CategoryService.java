
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	//	@Autowired
	//	private AdministratorService	adminService;

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int id) {
		return this.categoryRepository.findOne(id);
	}
	public Category createCategory() {
		Category c;
		c = new Category();

		c.setName("");
		//		c.setOtherlanguages(new ArrayList<String>());
		c.setCategories(new ArrayList<Category>());

		return c;
	}
	/*
	 * public Category saveParent(final Category c) {
	 * UserAccount userLogged;
	 * userLogged = LoginService.getPrincipal();
	 * Assert.isTrue(Utiles.findAuthority(userLogged.getAuthorities(), Authority.ADMIN));
	 * Administrator admin;
	 * admin = this.adminService.findOne();
	 * 
	 * Assert.notNull(admin);
	 * 
	 * Category result;
	 * Assert.notNull(c);
	 * result = this.categoryRepository.save(c);
	 * Assert.notNull(result);
	 * return result;
	 * 
	 * }
	 */
	public Category saveSubCategory(final int categoryParent, final Category newCategory) {

		Category result;
		result = this.categoryRepository.save(newCategory);

		Category aux;

		aux = this.categoryRepository.findOne(categoryParent);

		Collection<Category> category;
		category = aux.getCategories();

		category.add(result);

		aux.setCategories(category);

		return result;
	}
	/*
	 * public void deleteCategory(final int id) {
	 * UserAccount userLogged;
	 * userLogged = LoginService.getPrincipal();
	 * Assert.isTrue(Utiles.findAuthority(userLogged.getAuthorities(), Authority.ADMIN));
	 * Administrator admin;
	 * admin = this.adminService.findOne();
	 * 
	 * Assert.notNull(admin);
	 * 
	 * for (final FixUpTask f : this.categoryRepository.findAllFixUpTasksByCategory(id))
	 * f.setCategory(null);
	 * 
	 * for (final Finder f : this.categoryRepository.findAllFinderByCategory(id))
	 * f.setCategory(null);
	 * 
	 * this.categoryRepository.delete(id);
	 * 
	 * }
	 */
}
