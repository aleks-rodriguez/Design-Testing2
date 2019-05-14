
package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category extends DomainEntity {

	private String					name;
	private Map<String, String>		otherlanguages;
	private Collection<Category>	categories;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	//Position 0 is for spanish
	@ElementCollection(targetClass = String.class)
	@MapKeyColumn(name = "lang")
	public Map<String, String> getOtherlanguages() {
		return this.otherlanguages;
	}

	public void setOtherlanguages(final Map<String, String> otherlanguages) {
		this.otherlanguages = otherlanguages;
	}

}
