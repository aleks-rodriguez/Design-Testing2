
package services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
import domain.CreditCard;

@Service
@Transactional
public class ActorService extends AbstractService {

	@Autowired
	private AdministratorRepository	repository;


	public CreditCard createCreditCard() {
		CreditCard creditCard;
		creditCard = new CreditCard();
		creditCard.setHolder("");
		creditCard.setMake("");
		creditCard.setNumber("");
		creditCard.setExpiration(new Date());
		creditCard.setCvv(0);

		return creditCard;
	}
	public List<String> creditCardMakes() {
		List<String> makes;
		makes = Arrays.asList("VISA", "MASTERCARD", "AMEX", "DINERS", "FLY");
		return makes;
	}

	private String hashPassword(final String old) {
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		String passEncoded;
		passEncoded = encoder.encodePassword(old, null);

		return passEncoded;
	}

	public void delete(final int actorId) {
		Assert.notNull(LoginService.getPrincipal().getUsername());
		Actor a;

		if (super.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
			a = this.repository.findActorByUserAccountId(actorId);
			//			Assert.isTrue(this.adminRepository.getAdministratorByUserAccountId(LoginService.getPrincipal().getId()).getId() == actorId, "Delete not allowed");
			Assert.isTrue(LoginService.getPrincipal().getId() == actorId, "Delete not allowed");

			a.getAccount().setEnabled(false);
			a.setName("anonymous");
			a.setPhone("anonymous");
			a.setSurname("anonymous");
			a.setPhone("anonymous");
			a.setEmail("anonymous@email.anon");
			this.repository.save((Administrator) a);
		}
	}

}
