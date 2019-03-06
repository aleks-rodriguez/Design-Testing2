
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Area;

@Service
@Transactional
public class AreaService { //LOS ASSERT DEL PRINCIPAL

	@Autowired
	private AreaRepository		areaRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;


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
		final Area a;
		a = new Area();
		a.setName("");
		a.setPictures(new ArrayList<String>());
		return a;
	}
	public Area save(final Area a) {
		Area saved;
		Assert.notNull(a);
		saved = this.areaRepository.save(a);
		Assert.notNull(saved);
		return saved;
	}

	public void delete(final Area a) {
		Assert.notNull(a);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodsByAreaId(a.getId()).isEmpty());
		this.areaRepository.delete(a);
	}
}
