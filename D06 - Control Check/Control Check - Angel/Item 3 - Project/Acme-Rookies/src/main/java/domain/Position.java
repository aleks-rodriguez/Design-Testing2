
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import ticketable.Ticketable;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
	@Index(columnList = "ticker, title, description, deadline, profileRequired,skillsRequired,technologies")
})
public class Position extends Ticketable {

	private String	title;
	private String	description;
	private Date	deadline;
	private String	profileRequired;
	private String	skillsRequired;
	private String	technologies;
	private Double	salary;
	private boolean	finalMode;
	private boolean	cancel;
	private Company	company;


	@SafeHtml
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}
	@SafeHtml
	@NotBlank
	public String getProfileRequired() {
		return this.profileRequired;
	}

	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}
	@SafeHtml
	@NotBlank
	public String getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}
	@SafeHtml
	@NotBlank
	public String getTechnologies() {
		return this.technologies;
	}

	public void setTechnologies(final String technologies) {
		this.technologies = technologies;
	}
	@Min(value = 0)
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	public boolean isCancel() {
		return this.cancel;
	}

	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}

}
