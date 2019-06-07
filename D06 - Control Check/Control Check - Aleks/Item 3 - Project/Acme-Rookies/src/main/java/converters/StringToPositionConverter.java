
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PositionRepository;
import domain.Position;

@Component
@Transactional
public class StringToPositionConverter implements Converter<String, Position> {

	@Autowired
	PositionRepository	profileRepository;


	@Override
	public Position convert(final String text) {
		Position result;
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
