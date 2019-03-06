
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Enrolment extends DomainEntity {

	private Position	requestedPosition;
	private Position	assignededPosition;
	private Brotherhood	brotherhood;
	private Boolean		exitMember;
	private Date		exitMoment;


	public Boolean getExitMember() {
		return this.exitMember;
	}

	public void setExitMember(final Boolean exitMember) {
		this.exitMember = exitMember;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getExitMoment() {
		return this.exitMoment;
	}

	public void setExitMoment(final Date exitMoment) {
		this.exitMoment = exitMoment;
	}

	@OneToOne(optional = true)
	public Position getRequestedPosition() {
		return this.requestedPosition;
	}
	public void setRequestedPosition(final Position requestedPosition) {
		this.requestedPosition = requestedPosition;
	}
	@OneToOne(optional = true)
	public Position getAssignededPosition() {
		return this.assignededPosition;
	}
	public void setAssignededPosition(final Position assignededPosition) {
		this.assignededPosition = assignededPosition;
	}

	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
}
