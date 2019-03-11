
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Segment extends DomainEntity {

	private String	origin;
	private String	destiny;
	private Date	originTime;
	private Date	destinyTime;


	@NotBlank
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}
	@NotBlank
	public String getDestiny() {
		return this.destiny;
	}

	public void setDestiny(final String destiny) {
		this.destiny = destiny;
	}

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getOriginTime() {
		return this.originTime;
	}

	public void setOriginTime(final Date originTime) {
		this.originTime = originTime;
	}
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getDestinyTime() {
		return this.destinyTime;
	}

	public void setDestinyTime(final Date destinyTime) {
		this.destinyTime = destinyTime;
	}

}
