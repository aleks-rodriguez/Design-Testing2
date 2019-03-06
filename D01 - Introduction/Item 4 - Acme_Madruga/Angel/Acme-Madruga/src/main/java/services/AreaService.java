
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
import domain.Area;
import domain.Brotherhood;

@Service
@Transactional
public class AreaService { //LOS ASSERT DEL PRINCIPAL

	@Autowired
	private AreaRepository	areaRepository;


	public Brotherhood findByUserAccount() {
		return this.areaRepository.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
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

	public boolean setAreaToBrotherhood(final int area) {
		boolean res = false;
		Brotherhood b;
		b = this.areaRepository.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final Area a = this.areaRepository.findOne(area);
		if (b.getArea() == null) {
			b.setArea(a);
			res = true;
		}
		return res;
	}
	public void delete(final Area a) {
		Assert.notNull(a);
		Assert.isTrue(this.areaRepository.getBrotherhoodsByAreaId(a.getId()).isEmpty());
		this.areaRepository.delete(a);
	}
}
