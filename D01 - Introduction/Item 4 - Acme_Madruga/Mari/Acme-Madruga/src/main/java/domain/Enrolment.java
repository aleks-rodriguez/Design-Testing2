package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Enrolment {
	private String		requestedPosition;
	private String		assignededPosition;

 // private Brotherhood  brotherhood;


	
	@NotBlank
	public String getRequestedPosition() {
		return requestedPosition;
	}
	public void setRequestedPosition(String requestedPosition) {
		this.requestedPosition = requestedPosition;
	}
	
	@NotBlank
	public String getAssignededPosition() {
		return assignededPosition;
	}
	public void setAssignededPosition(String assignededPosition) {
		this.assignededPosition = assignededPosition;
	}
	

	
//	@ManyToOne(optional = false)
//	public Brotherhood getBrotherhood() {
//		return this.brotherhood;
//	}
//	public void Brotherhood(final Brotherhood brotherhood) {
//		this.brotherhood = brotherhood;
//	}
}
