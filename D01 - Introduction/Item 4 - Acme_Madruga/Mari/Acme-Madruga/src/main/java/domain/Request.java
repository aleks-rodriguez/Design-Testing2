
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
	private int		marchRow;
	private int		marchColumn;
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
	public int getMarchRow() {
		return this.marchRow;
	}

	public void setMarchRow(final int marchRow) {
		this.marchRow = marchRow;
	}
	@NotNull
	@Min(value = 1)
	public int getMarchColumn() {
		return this.marchColumn;
	}

	public void setMarchColumn(final int marchColumn) {
		this.marchColumn = marchColumn;
	}

	public String getReasonWhyRejected() {
		return this.reasonWhyRejected;
	}

	public void setReasonWhyRejected(final String reasonWhyRejected) {
		this.reasonWhyRejected = reasonWhyRejected;
	}

}
