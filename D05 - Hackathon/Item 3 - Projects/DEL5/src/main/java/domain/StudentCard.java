
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class StudentCard {

	private String	center;
	private int		code;


	@SafeHtml
	@NotBlank
	public String getCenter() {
		return this.center;
	}

	public void setCenter(final String center) {
		this.center = center;
	}
	@Min(value = 4)
	@Max(value = 4)
	public int getCode() {
		return this.code;
	}

	public void setCode(final int code) {
		this.code = code;
	}

}
