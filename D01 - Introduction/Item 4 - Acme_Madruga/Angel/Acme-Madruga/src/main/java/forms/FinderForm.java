
package forms;

import java.util.Date;

import domain.Area;

public class FinderForm {

	private int		id;
	private String	singleWord;
	private Area	area;
	private Date	minimunDate;
	private Date	maximumDate;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

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

	public Date getMinimunDate() {
		return this.minimunDate;
	}

	public void setMinimunDate(final Date minimunDate) {
		this.minimunDate = minimunDate;
	}

	public Date getMaximumDate() {
		return this.maximumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

}
