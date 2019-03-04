
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
	@Index(columnList = "ticker, title, description, momentOrganised")
})
public class Procession extends DomainEntity {

	private String				ticker;
	private String				title;
	private String				description;
	private Date				momentOrganised;
	private Boolean				finalMode;
	private Collection<Request>	requests;
	private Collection<Float>	floats;


	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@OneToMany
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-([\\w]{5}$|[\\w]{6}$)")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
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

	@ManyToMany
	public Collection<Float> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;
	}

}
