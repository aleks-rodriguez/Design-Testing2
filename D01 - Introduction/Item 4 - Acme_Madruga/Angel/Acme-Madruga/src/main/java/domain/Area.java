
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Area extends DomainEntity {//le pongo el notnull porque, aunque no lo diga explicitamente, no creo que puedas tener un area sin nombre ni nada

	////y si mal no recuerdo junto al elementCollection no pones mas nada porque si no BUM al popular

	private String				name;
	private Collection<String>	pictures;	//dice "un numero de fotos" pero yo entiendo que eso se refiere a que tenga unas cuantas fotos asociadas


	@NotNull
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
}
