
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Enrolment extends DomainEntity {

	private Position	requestedPosition;
	private Position	assignededPosition;
	private Brotherhood	brotherhood;


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
