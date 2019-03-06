
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Request extends DomainEntity {

	private String	status;
	private Integer	marchRow;
	private Integer	marchColumn;
	private String	record;


	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Min(value = 1)
	public Integer getMarchRow() {
		return this.marchRow;
	}

	public void setMarchRow(final Integer marchRow) {
		this.marchRow = marchRow;
	}

	@Min(value = 1)
	public Integer getMarchColumn() {
		return this.marchColumn;
	}

	public void setMarchColumn(final Integer marchColumn) {
		this.marchColumn = marchColumn;
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(final String record) {
		this.record = record;
	}

}
