
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Request extends DomainEntity {

	private String	status;
	private Integer	marchRow;
	private Integer	marchColumn;
	private String	reasonWhyRejected;


	@NotNull
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	@NotNull
	@Min(value = 1)
	public Integer getMarchRow() {
		return this.marchRow;
	}

	public void setMarchRow(final Integer marchRow) {
		this.marchRow = marchRow;
	}
	@NotNull
	@Min(value = 1)
	public Integer getMarchColumn() {
		return this.marchColumn;
	}

	public void setMarchColumn(final Integer marchColumn) {
		this.marchColumn = marchColumn;
	}

	public String getReasonWhyRejected() {
		return this.reasonWhyRejected;
	}

	public void setReasonWhyRejected(final String reasonWhyRejected) {
		this.reasonWhyRejected = reasonWhyRejected;
	}

}
