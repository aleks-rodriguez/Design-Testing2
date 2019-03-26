
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Sponsor extends Actor {

	private Collection<Sponsorship>	sponsorships;


	@OneToMany(mappedBy = "sponsor")
	public Collection<Sponsorship> getSponsorship() {
		return this.sponsorships;
	}

	public void setSponsorship(final Collection<Sponsorship> sponsorship) {
		this.sponsorships = sponsorship;
	}

}
