
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Sponsorship extends DomainEntity {

	private String		urlBanner;
	private String		linkTPage;
	private Sponsor		sponsor;
	private Parade		parade;
	private boolean		isActive;

	private CreditCard	creditCard;


	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(final boolean isActive) {
		this.isActive = isActive;
	}

	@URL
	@NotBlank
	public String getUrlBanner() {
		return this.urlBanner;
	}

	public void setUrlBanner(final String urlBanner) {
		this.urlBanner = urlBanner;
	}

	@URL
	@NotBlank
	public String getLinkTPage() {
		return this.linkTPage;
	}
	public void setLinkTPage(final String linkTPage) {
		this.linkTPage = linkTPage;
	}

	@ManyToOne(optional = false)
	@NotNull
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@OneToOne
	@NotNull
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
