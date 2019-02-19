
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Finder extends DomainEntity {

	private String	singleWord;
	private Area	area;
	private Date	minimunDate;
	private Date	maximumDate;


	//	Collection<Proccesion> proccesions;

	public String getSingleWord() {
		return this.singleWord;
	}

	public void setSingleWord(final String singleWord) {
		this.singleWord = singleWord;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMinimunDate() {
		return this.minimunDate;
	}

	public void setMinimunDate(final Date minimunDate) {
		this.minimunDate = minimunDate;
	}
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMaximumDate() {
		return this.maximumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	//	@ManyToMany
	//	public Collection<Proccesion> getProccesions() {
	//		return proccesions;
	//	}

	//	public void setProccesions(Collection<Proccesion> proccesions) {
	//		this.proccesions = proccesions;
	//	}
}
