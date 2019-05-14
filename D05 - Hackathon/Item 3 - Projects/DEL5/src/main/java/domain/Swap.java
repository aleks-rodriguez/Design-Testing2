
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Swap extends DomainEntity {

	private String			phone;
	private String			status;
	private String			description;
	private Organization	organization;
	private Collaborator	isReceiver;
	private Collaborator	isSender;


	@NotBlank
	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@OneToOne
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(final Organization organization) {
		this.organization = organization;
	}

	@ManyToOne
	public Collaborator getIsReceiver() {
		return this.isReceiver;
	}

	public void setIsReceiver(final Collaborator isReceiver) {
		this.isReceiver = isReceiver;
	}

	@ManyToOne
	public Collaborator getIsSender() {
		return this.isSender;
	}

	public void setIsSender(final Collaborator isSender) {
		this.isSender = isSender;
	}

}
