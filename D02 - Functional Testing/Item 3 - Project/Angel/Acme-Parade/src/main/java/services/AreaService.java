
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

@Service
@Transactional
public class AreaService { //LOS ASSERT DEL PRINCIPAL

	@Autowired
	private AreaRepository	areaRepository;


	public Actor findActorByUserAccount(final int id) {
		return this.areaRepository.findActorByUserAccount(id);
	}

	public Collection<Area> findAll() {
		Collection<Area> area;
		area = this.areaRepository.findAll();
		Assert.notNull(area);
		return area;
	}

	public Area findOne(final int id) {
		Area a;
		a = this.areaRepository.findOne(id);
		Assert.notNull(a);
		return a;
	}

	public Area create() {
		Area a;
		a = new Area();
		a.setName("");
		a.setPictures(new ArrayList<String>());
		return a;
	}
	public Area save(final Area a) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN));
		Area saved;
		saved = this.areaRepository.save(a);
		return saved;
	}

	public boolean setArea(final int area) {
		boolean res = false;
		Actor a;
		a = this.areaRepository.findActorByUserAccount(LoginService.getPrincipal().getId());
		Area ar;
		ar = this.areaRepository.findOne(area);
		if (Utiles.findAuthority(a.getAccount().getAuthorities(), Authority.BROTHERHOOD)) {
			final Brotherhood b = (Brotherhood) a;
			if (b.getArea() == null) {
				b.setArea(ar);
				res = true;
			}
		} else if (Utiles.findAuthority(a.getAccount().getAuthorities(), Authority.CHAPTER)) {
			final Chapter c = (Chapter) a;
			if (c.getArea() == null) {
				c.setArea(ar);
				res = true;
			}
		}

		return res;
	}

	public void delete(final Area a) {
		Assert.notNull(a);
		Assert.isTrue(this.areaRepository.getBrotherhoodsByAreaId(a.getId()).isEmpty());
		this.areaRepository.delete(a);
	}
}
