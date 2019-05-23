
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService	serviceCategory;


	public CategoryController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@CookieValue(value = "language", required = false) String lang) {
		ModelAndView result;
		result = new ModelAndView("category/list");
		result.addObject("categories", this.serviceCategory.findAll());
		if (lang == null || lang == "")
			lang = "en";
		result.addObject("lang", lang);
		result.addObject("requestURI", "category/administrator/list.do");
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int parent) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", this.serviceCategory.createCategory());
		result.addObject("parent", parent);
		result.addObject("requestURI", "category/administrator/edit.do?parent=" + parent + "&check=true");
		return result;
	}
	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView edit(@RequestParam final boolean check, @RequestParam final int parent, @Valid final Category category, final BindingResult binding) {
	 * ModelAndView result;
	 * 
	 * if (binding.hasErrors())
	 * result = this.createEditModelAndView(category);
	 * else
	 * try {
	 * if (check) {
	 * if (parent == 0)
	 * this.serviceCategory.saveParent(category);
	 * else
	 * this.serviceCategory.saveSubCategory(parent, category);
	 * 
	 * } else
	 * this.serviceCategory.saveParent(category);
	 * 
	 * result = new ModelAndView("redirect:list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(category, "category.commit.error");
	 * }
	 * return result;
	 * }
	 *///Updating a warranty
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int parent) {
		ModelAndView result;
		Category aux;
		aux = this.serviceCategory.findOne(parent);
		Assert.notNull(aux);
		result = this.createEditModelAndView(aux);
		result.addObject("requestURI", "category/administrator/edit.do?parent=" + parent + "&check=false");
		return result;
	}
	//Delete a category
	/*
	 * @RequestMapping(value = "/delete", method = RequestMethod.GET)
	 * public ModelAndView delete(final int cat) {
	 * ModelAndView result;
	 * Category category = null;
	 * try {
	 * category = this.serviceCategory.findOne(cat);
	 * this.serviceCategory.deleteCategory(category.getId());
	 * result = new ModelAndView("redirect:list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(category, "category.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 */protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;
		result = this.createEditModelAndView(category, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("message", message);

		return result;
	}
}
