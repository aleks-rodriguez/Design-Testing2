
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String				name;
	private boolean				fromSystem;
	private Collection<Mesage>	mesage;
	private Collection<Box>		boxes;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isFromSystem() {
		return this.fromSystem;
	}

	public void setFromSystem(final boolean fromSystem) {
		this.fromSystem = fromSystem;
	}

	@ManyToMany(mappedBy = "box")
	public Collection<Mesage> getMesage() {
		return this.mesage;
	}

	public void setMesage(final Collection<Mesage> mesage) {
		this.mesage = mesage;
	}

	@ElementCollection
	public Collection<Box> getBoxes() {
		return this.boxes;
	}

	public void setBoxes(final Collection<Box> boxes) {
		this.boxes = boxes;
	}

}
