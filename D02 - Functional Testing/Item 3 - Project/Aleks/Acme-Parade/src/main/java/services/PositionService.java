
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	repositoryPosition;


	public Collection<Position> findAll() {
		return this.repositoryPosition.findAll();
	}

	public Position findOne(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		return this.repositoryPosition.findOne(id);
	}

	public Position create() {

		Position position;
		position = new Position();

		position.setName("");
		position.setOtherLangs(new ArrayList<String>());

		return position;
	}

	public Position save(final Position position) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));

		Position saved;

		saved = this.repositoryPosition.save(position);

		return saved;
	}

	public boolean delete(final int id) {
		boolean res;
		res = false;
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Position position;
		position = this.repositoryPosition.findOne(id);
		this.repositoryPosition.delete(position);
		return res;
	}
}
