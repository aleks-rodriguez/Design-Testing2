
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SegmentRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Coordinate;
import domain.Parade;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	@Autowired
	private SegmentRepository	repositorySegment;

	@Autowired
	private ParadeService		serviceParade;

	@Autowired
	private Validator			validator;


	public Collection<Segment> findAllSegmentsByParadeId(final int id) {
		return this.repositorySegment.findAllSegmentsByParadeId(id);
	}

	public Segment findOne(final int id) {
		return this.repositorySegment.findOne(id);
	}

	public Coordinate createCoordinate() {
		Coordinate coordinate;
		coordinate = new Coordinate();

		coordinate.setLatitude(0.0);
		coordinate.setLongitude(0.0);

		return coordinate;
	}

	public Collection<Segment> findAllSegmentsByParadeIdClosePath(final int id) {
		return this.repositorySegment.findAllSegmentsByParadeIdClosePath(id);
	}

	public void openPath(final int parad) {

		Parade parade;
		parade = this.serviceParade.findOne(parad);

		//Saco la lista de segmentos
		List<Segment> segments;
		segments = new ArrayList<Segment>(this.repositorySegment.findAllSegmentsByParadeId(parade.getId()));

		//Cojo el ultimo segmento el de mayor number
		Segment s;
		s = segments.get(segments.size() - 1);

		//Elimino el segmento 
		this.delete(s.getId());

		//Pongo el path de las parades a true
		parade.setOpenPath(true);
	}

	public Segment closePath(final int parade) {

		Parade p;
		p = this.serviceParade.findOne(parade);
		p.setOpenPath(false);
		//Saco la lista de segmentos
		List<Segment> segments;
		segments = new ArrayList<Segment>(this.repositorySegment.findAllSegmentsByParadeId(parade));

		//Segmento inicial por orden de number
		Segment seg0;
		seg0 = segments.get(0);

		//Segmento ultimo
		Segment segLast;
		segLast = segments.get(segments.size() - 1);

		//Copia para el segmento artificial
		Segment segment;
		segment = this.create(p);
		segment.setNumber(segLast.getNumber() + 1);
		segment.setSegment(seg0.getSegment());
		return segment;
	}

	public Segment create(final Parade parade) {
		Brotherhood b;
		b = this.serviceParade.findBrotherhoodByUser(LoginService.getPrincipal().getId());
		Assert.isTrue(b.getParades().contains(parade));
		Segment segment;
		segment = new Segment();
		segment.setSegment(this.createCoordinate());
		segment.setArriveTime(new Date());
		segment.setParade(parade);
		segment.setNumber(0);
		return segment;
	}

	public Segment save(final Segment segment) {

		final Parade p = segment.getParade();

		final Brotherhood b = this.serviceParade.findBrotherhoodByParade(p.getId());

		Assert.isTrue(LoginService.getPrincipal().getId() == b.getAccount().getId());

		Segment result;

		result = this.repositorySegment.save(segment);

		return result;
	}

	public Segment reconstruct(final Segment segment, final BindingResult binding) {
		Segment result = null;

		if (segment.getId() == 0)
			result = segment;
		else {
			result = this.repositorySegment.findOne(segment.getId());
			result.setSegment(segment.getSegment());
			result.setArriveTime(segment.getArriveTime());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public void delete(final int id) {

		Segment segment;
		segment = this.repositorySegment.findOne(id);
		final Parade p = segment.getParade();
		final Brotherhood b = this.serviceParade.findBrotherhoodByParade(p.getId());

		Assert.isTrue(LoginService.getPrincipal().getId() == b.getAccount().getId());
		this.repositorySegment.delete(segment);
		Collection<Segment> segments;
		segments = this.repositorySegment.findAllSegmentsByParadeId(p.getId());
		if (segments.isEmpty())
			p.setOpenPath(true);

	}

}
