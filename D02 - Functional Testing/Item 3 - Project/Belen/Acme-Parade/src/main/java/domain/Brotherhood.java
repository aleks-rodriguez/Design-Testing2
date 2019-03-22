
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Brotherhood extends Actor {

	private String					title;
	private Date					establishment;
	private Collection<String>		pictures;
	private Collection<Enrolment>	enrolments;
	private Area					area;
	private Collection<Float>		floats;
	private Collection<Parade>		parades;
	private History					history;


	@OneToMany
	public Collection<Parade> getParades() {
		return this.parades;
	}

	public void setParades(final Collection<Parade> parades) {
		this.parades = parades;
	}
	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEstablishment() {
		return this.establishment;
	}
	public void setEstablishment(final Date establishment) {
		this.establishment = establishment;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}
	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@OneToMany(mappedBy = "brotherhood")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}
	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}
	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}
	public void setArea(final Area area) {
		this.area = area;
	}

	@OneToMany
	public Collection<Float> getFloats() {
		return this.floats;
	}
	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;
	}

	@OneToOne
	public History getHistory() {
		return this.history;
	}

	public void setHistory(final History history) {
		this.history = history;
	}

}
