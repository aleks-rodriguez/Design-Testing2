
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Segment extends DomainEntity {

	private Coordinate	origin;
	private Coordinate	destiny;
	private Date		originTime;
	private Date		destinyTime;
	private Parade		parade;


	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "latitudeOrigin")), @AttributeOverride(name = "longitude", column = @Column(name = "longitudeOrigin"))
	})
	public Coordinate getOrigin() {
		return this.origin;
	}

	public void setOrigin(final Coordinate origin) {
		this.origin = origin;
	}
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "latitudeDestiny")), @AttributeOverride(name = "longitude", column = @Column(name = "longitudeDestiny"))
	})
	public Coordinate getDestiny() {
		return this.destiny;
	}

	public void setDestiny(final Coordinate destiny) {
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

	@ManyToOne
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

}
