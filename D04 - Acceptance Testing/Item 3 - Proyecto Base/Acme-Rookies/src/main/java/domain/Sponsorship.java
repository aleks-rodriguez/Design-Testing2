
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Sponsorship extends DomainEntity {

	private CreditCard	creditCard;
	private String		banner;
	private String		target;
	private Position	position;
	private Provider	provider;
	private Double		flat_rate;
	private boolean		isActive;


	@Valid
	@Embedded
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getTarget() {
		return this.target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@Min(0)
	public Double getFlat_rate() {
		return this.flat_rate;
	}

	public void setFlat_rate(final Double flat_rate) {
		this.flat_rate = flat_rate;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(final boolean isActive) {
		this.isActive = isActive;
	}
}
