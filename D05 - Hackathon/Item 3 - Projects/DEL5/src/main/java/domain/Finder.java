
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Finder extends DomainEntity {

	private String					singleKey;
	private Date					deadline;
	private Double					minSalary;
	private Double					maxSalary;
	private Date					creationDate;
	private Collection<Proclaim>	proclaims;


	@ManyToMany
	public Collection<Proclaim> getProclaims() {
		return this.proclaims;
	}

	public void setProclaims(final Collection<Proclaim> proclaims) {
		this.proclaims = proclaims;
	}

	@SafeHtml
	public String getSingleKey() {
		return this.singleKey;
	}

	public void setSingleKey(final String singleKey) {
		this.singleKey = singleKey;
	}
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@Min(value = 0)
	public Double getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Double minSalary) {
		this.minSalary = minSalary;
	}
	@Min(value = 0)
	public Double getMaxSalary() {
		return this.maxSalary;
	}

	public void setMaxSalary(final Double maxSalary) {
		this.maxSalary = maxSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm'Z'")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

}
