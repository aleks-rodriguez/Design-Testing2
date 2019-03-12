
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

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

	@Autowired(required = false)
	private Validator			validator;


	public Segment findOne(final int id) {
		return this.repositorySegment.findOne(id);
	}

	public Segment create(final Parade parade, final String contiguous) {
		Segment segment;
		segment = new Segment();

		segment.setOrigin(contiguous == null ? "" : contiguous);
		segment.setDestiny("");
		segment.setOriginTime(new Date());
		segment.setDestinyTime(new Date());
		segment.setParade(parade);
		return segment;
	}

	public Segment save(final Segment segment) {

		Segment result;

		result = this.repositorySegment.save(segment);

		return result;
	}

	public void delete(final int id) {

		final Parade parade = this.repositorySegment.findParadeBySegment(id);
		final Brotherhood b = this.serviceParade.findBrotherhoodByUser(LoginService.getPrincipal().getId());
		final Collection<Parade> parades = b.getParades();

		if (parades.contains(parade)) {
			Segment segment;
			segment = this.repositorySegment.findOne(id);

			this.repositorySegment.delete(segment);
		}

	}

}
