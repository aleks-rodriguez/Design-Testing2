
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Actor extends DomainEntity {

	private String				name;
	private String				surname;
	private String				photo;
	private String				email;
	private String				phone;
	private String				adress;
	private double				vat;
	private CreditCard			creditCard;
	private Collection<Profile>	profiles;
	private UserAccount			account;
	private boolean				spammer;


	@NotNull
	@Valid
	@OneToOne(cascade = {
		CascadeType.ALL
	}, optional = false)
	@JoinColumn(name = "user_account_id")
	public UserAccount getAccount() {
		return this.account;
	}
	public void setAccount(final UserAccount account) {
		this.account = account;
	}

	@OneToMany
	public Collection<Profile> getProfiles() {
		return this.profiles;
	}

	public void setProfiles(final Collection<Profile> profiles) {
		this.profiles = profiles;
	}

	public boolean isSpammer() {
		return this.spammer;
	}

	public void setSpammer(final boolean spammer) {
		this.spammer = spammer;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	@SafeHtml
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@Pattern(regexp = "^([a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+)|([\\w\\s]+<[a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+>+)|([0-9a-zA-Z]([-.\\\\w]*[0-9a-zA-Z])+@+(?=\\?))|([\\w\\s]+<[a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@+>)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
	@SafeHtml
	public String getAdress() {
		return this.adress;
	}

	public void setAdress(final String adress) {
		this.adress = adress;
	}

	@Range(min = 0, max = 1)
	public double getVat() {
		return this.vat;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}

	@Embedded
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
