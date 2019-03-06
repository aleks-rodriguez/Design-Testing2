
package forms;

public class RequestForm {

	private int		id;
	private String	status;
	private Integer	marchRow;
	private Integer	marchColumn;
	private String	record;


	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Integer getMarchRow() {
		return this.marchRow;
	}

	public void setMarchRow(final Integer marchRow) {
		this.marchRow = marchRow;
	}

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

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
