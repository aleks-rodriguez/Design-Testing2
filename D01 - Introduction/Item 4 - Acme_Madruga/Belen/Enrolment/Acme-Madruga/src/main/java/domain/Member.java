
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Member extends Actor {

	private Collection<Request>	requests;
	private Finder				finder;


	@OneToMany
	public Collection<Request> getRequests() {
		return this.requests;
	}
	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}
	public void setFinder(final Finder finder) {
		this.finder = finder;
	}
}
