
package domain;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Processions extends DomainEntity {

	private String	ticker;
	private String	title;
	private String	description;
	private Date	momentOrganised;


	//	private Request	requests;
	//	private Collection<Floatt>	floatts;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229):\\w{6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMomentOrganised() {
		return this.momentOrganised;
	}

	public void setMomentOrganised(final Date momentOrganised) {
		this.momentOrganised = momentOrganised;
	}

	//  @OneToMany
	//	public Request getRequests() {
	//		return requests;
	//	}
	//
	//	public void setRequests(final Request requests) {
	//		this.requests = requests;
	//	}

	//	@ManyToMany
	//	public Collection<Floatt> getFloatts() {
	//		return this.floatts;
	//	}
	//
	//	public void setFloatts(final Collection<Floatt> floatts) {
	//		this.floatts = floatts;
	//	}

}
