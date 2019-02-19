
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
public class Floatt extends DomainEntity {

	private String				title;
	private String				description;
	private Collection<String>	pictures;


	//	private Collection<Processions>	processions;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	//	@ManyToMany(mappedBy = "floatt")
	//	public Collection<Processions> getProcessions() {
	//		return this.processions;
	//	}
	//
	//	public void setProcessions(final Collection<Processions> processions) {
	//		this.processions = processions;
	//	}

}
