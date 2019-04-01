
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Actor extends DomainEntity {

	private Collection<Profile>	profiles;
	private UserAccount			account;
	private boolean				spammer;


	@NotNull
	@Valid
	@OneToOne(cascade = {
		CascadeType.ALL
	}, optional = false)
	@JoinColumn(name = "user_account_id")
	public UserAccount getAccount() {
		return this.account;
	}
	public void setAccount(final UserAccount account) {
		this.account = account;
	}

	@OneToMany
	public Collection<Profile> getProfiles() {
		return this.profiles;
	}

	public void setProfiles(final Collection<Profile> profiles) {
		this.profiles = profiles;
	}

	public boolean isSpammer() {
		return this.spammer;
	}

	public void setSpammer(final boolean spammer) {
		this.spammer = spammer;
	}

}
