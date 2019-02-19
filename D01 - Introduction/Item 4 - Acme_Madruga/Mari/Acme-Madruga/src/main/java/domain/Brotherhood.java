package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Brotherhood extends Actor{
	
	private String					title;
	private Date 					establishment;
	private Collection <String>		pictures;
	//private Collection <Enrolment> enrolments;
	//private Area 					area;
	//private Collection <Float>   floats;
	
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@NotBlank
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEstablishment() {
		return establishment;
	}
	public void setEstablishment(Date establishment) {
		this.establishment = establishment;
	}
	
	
	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}
	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}

	
	//@OneToMany(mappedBy = "brotherhood")
	//public Collection <Enrolment> getEnrolments() {
	//return enrolments;
	
	//public void setEnrolments(final Enrolment enrolments) {
	//this.enrolments = enrolments;
	
	//@OneToOne(optional = "false")
	//	public Area getArea() {
	//		return area;
	//	}
	//	public void setArea(Area area) {
	//		this.area = area;
	//	}
	
	//@OneToMany
	//public Collection <Float> getFloats() {
	//return floats;
	
	//public void setFloats (final Float floats) {
	//this.floats = floats;
}
