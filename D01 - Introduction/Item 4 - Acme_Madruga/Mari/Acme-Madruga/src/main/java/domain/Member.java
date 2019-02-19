package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Member extends Actor {

	//private Collection <Enrolment> enrolments;
	//private Collection <Request>   requests;
	// private Finder		         finder;	
	
	
	//@OneToMany(mappedBy = "member")
	//public Collection <Enrolment> getEnrolments() {
	//return enrolments;
	
	//public Collection <Enrolment> setEnrolments(final Enrolment enrolments) {
	//this.enrolments = enrolments;
	
	//@OneToMany
	//public Collection <Request> getRequests() {
	//return requests;
	
	//public Collection <Request> setRequests(final Request requests) {
	//this.requests = requests;
	
	//@OneToOne(optional = "false")
	//	public Finder getFinder() {
	//		return finder;
	//	}
	//	public void setFinder(Finder finder) {
	//		this.finder = finder;
	//	}
}
