
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holder;
	private String	make;
	private String	number;
	private Date	expiration;
	private int		cvv;


	@SafeHtml
	@NotBlank
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@SafeHtml
	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@Min(value = 16)
	@Max(value = 16)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Min(value = 3)
	@Max(value = 3)
	public int getCvv() {
		return this.cvv;
	}

	public void setCvv(final int cvv) {
		this.cvv = cvv;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public void setExpiration(final Date expiration) {
		this.expiration = expiration;
	}

}
