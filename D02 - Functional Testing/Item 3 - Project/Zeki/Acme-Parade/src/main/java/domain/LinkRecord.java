
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class LinkRecord extends DomainEntity {

	private String		title;
	private String		description;
	private Brotherhood	brotherhood;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@OneToOne
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
