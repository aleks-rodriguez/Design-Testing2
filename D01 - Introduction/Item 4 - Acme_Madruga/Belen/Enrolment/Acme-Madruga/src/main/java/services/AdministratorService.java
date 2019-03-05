
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.Utiles;
import domain.Administrator;
import domain.Box;
import domain.Profile;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	adminRepository;


	//	@Autowired
	//private BoxService				boxService;
	public Administrator findOne(final int userAccount) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Administrator saved;
		saved = this.adminRepository.getAdministratorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(saved);
		return saved;
	}
	public Administrator findAdmin(final int administrator) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Administrator saved;
		saved = this.adminRepository.findOne(administrator);
		Assert.notNull(saved);
		return saved;
	}
	public Administrator createAdministrator() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Administrator administrator;
		administrator = new Administrator();

		UserAccount user;
		user = new UserAccount();
		user.setUsername("");
		user.setPassword("");
		user.setEnabled(true);
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		user.addAuthority(authority);

		administrator.setAccount(user);
		administrator.setProfiles(new ArrayList<Profile>());
		administrator.setAdress("");
		administrator.setBoxes(new ArrayList<Box>());
		administrator.setEmail("");
		administrator.setMiddleName("");
		administrator.setName("");
		administrator.setPhone("");
		administrator.setPhoto("");
		administrator.setSurname("");

		return administrator;
	}

	public Administrator save(final Administrator admin) {

		if (admin.getId() != 0) {
			final UserAccount account = this.findAdmin(admin.getId()).getAccount();
			account.setUsername(admin.getAccount().getUsername());
			account.setPassword(Utiles.hashPassword(admin.getAccount().getPassword()));
			account.setAuthorities(admin.getAccount().getAuthorities());
			admin.setAccount(account);
		} else
			admin.getAccount().setPassword(Utiles.hashPassword(admin.getAccount().getPassword()));
		//final Collection<Box> boxes = this.boxService.save(Utiles.initBoxes());
		//admin.setBoxes(boxes);

		Administrator modify;

		modify = this.adminRepository.saveAndFlush(admin);

		return modify;
	}
}
