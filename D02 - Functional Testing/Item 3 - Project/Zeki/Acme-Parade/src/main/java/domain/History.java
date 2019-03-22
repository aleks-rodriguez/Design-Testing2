
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class History extends DomainEntity {

	private Collection<LinkRecord>			linkRecord;
	private Collection<PeriodRecord>		periodRecord;
	private Collection<LegalRecord>			legalRecord;
	private Collection<MiscellaneousRecord>	miscellaneousRecord;
	private InceptionRecord					inceptionRecord;


	@OneToMany
	public Collection<LinkRecord> getLinkRecord() {
		return this.linkRecord;
	}

	public void setLinkRecord(final Collection<LinkRecord> linkRecord) {
		this.linkRecord = linkRecord;
	}

	@OneToMany
	public Collection<PeriodRecord> getPeriodRecord() {
		return this.periodRecord;
	}

	public void setPeriodRecord(final Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}

	@OneToMany
	public Collection<LegalRecord> getLegalRecord() {
		return this.legalRecord;
	}

	public void setLegalRecord(final Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}

	@OneToMany
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@OneToOne
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

}
