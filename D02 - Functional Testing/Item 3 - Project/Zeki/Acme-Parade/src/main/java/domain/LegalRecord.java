
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class LegalRecord extends DomainEntity {

	private String				title;
	private String				description;
	private String				legalName;
	private int					vat;
	private Collection<String>	laws;


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

	@NotBlank
	@SafeHtml
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	@Range(min = 0, max = 100)
	public int getVat() {
		return this.vat;
	}

	public void setVat(final int vat) {
		this.vat = vat;
	}

	@ElementCollection
	public Collection<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final Collection<String> laws) {
		this.laws = laws;
	}

}
