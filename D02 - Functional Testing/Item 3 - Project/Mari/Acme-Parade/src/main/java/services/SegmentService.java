
package services;

import java.util.Collection;
import java.util.Date;

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

	}

}
