
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Position extends DomainEntity {

	private String				name;
	private Collection<String>	otherLangs;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection
	public Collection<String> getOtherLangs() {
		return this.otherLangs;
	}

	public void setOtherLangs(final Collection<String> otherLangs) {
		this.otherLangs = otherLangs;
	}

}