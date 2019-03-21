
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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

	private Coordinate	segment;
	private Date		arriveTime;
	private Parade		parade;


	@Embedded
	public Coordinate getSegment() {
		return this.segment;
	}

	public void setSegment(final Coordinate segment) {
		this.segment = segment;
	}

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getArriveTime() {
		return this.arriveTime;
	}

	public void setArriveTime(final Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	@ManyToOne
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

}
