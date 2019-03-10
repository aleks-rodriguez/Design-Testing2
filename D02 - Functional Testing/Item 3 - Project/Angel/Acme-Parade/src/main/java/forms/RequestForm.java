
package forms;

public class RequestForm {

	private int		id;
	private String	status;
	private int		marchRow;
	private int		marchColumn;
	private String	record;


	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public int getMarchRow() {
		return this.marchRow;
	}

	public void setMarchRow(final int marchRow) {
		this.marchRow = marchRow;
	}

	public int getMarchColumn() {
		return this.marchColumn;
	}

	public void setMarchColumn(final int marchColumn) {
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
