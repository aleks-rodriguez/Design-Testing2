
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SegmentRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	@Autowired
	private SegmentRepository	repositorySegment;

	@Autowired
	private ParadeService		serviceParade;


	public Segment findOne(final int id) {
		return this.repositorySegment.findOne(id);
	}

	public Segment create(final String contiguous) {
		Segment segment;
		segment = new Segment();

		segment.setOrigin(contiguous == null ? "" : contiguous);
		segment.setDestiny("");
		segment.setOriginTime(new Date());
		segment.setDestinyTime(new Date());

		return segment;
	}

	public Segment save(final int parade, final Segment segment) {

		Parade p;
		p = this.serviceParade.findOne(parade);

		Segment result;

		result = this.repositorySegment.save(segment);

		Collection<Segment> segments;
		segments = p.getSegments();

		if (!segments.contains(result)) {
			segments.add(segment);
			p.setSegments(segments);
		}

		return result;
	}

	public void delete(final int id) {

		final Parade parade = this.repositorySegment.findParadeBySegment(id);
		final Brotherhood b = this.serviceParade.findBrotherhoodByUser(LoginService.getPrincipal().getId());
		final Collection<Parade> parades = b.getParades();

		if (parades.contains(parade)) {
			Segment segment;
			segment = this.repositorySegment.findOne(id);

			Collection<Segment> segments;
			segments = parade.getSegments();
			segments.remove(segment);
			parade.setSegments(segments);

			this.repositorySegment.delete(segment);
		}

	}

}
