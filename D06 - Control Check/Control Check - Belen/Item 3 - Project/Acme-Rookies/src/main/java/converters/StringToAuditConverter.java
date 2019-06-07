
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AuditRepository;
import domain.Audit;

@Component
@Transactional
public class StringToAuditConverter implements Converter<String, Audit> {

	@Autowired
	AuditRepository	profileRepository;


	@Override
	public Audit convert(final String text) {
		Audit result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.profileRepository.findOne(id);
			}
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
