
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Provider extends Actor {

	private String	make;


	@Column(name = "providerMake")
	@NotBlank
	@SafeHtml
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}
}
