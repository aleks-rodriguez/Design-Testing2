
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EnrolmentRepository;
import domain.Enrolment;

@Component
@Transactional
public class StringToEnrolmentConverter implements Converter<String, Enrolment> {

	@Autowired
	EnrolmentRepository	enrolmentRepository;


	@Override
	public Enrolment convert(final String text) {
		Enrolment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				System.out.println(id);
				result = this.enrolmentRepository.findOne(id);
			}
			System.out.println(result);
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
