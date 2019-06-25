
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.OmamekRepository;
import domain.Omamek;

@Component
@Transactional
public class StringToOmamekConverter implements Converter<String, Omamek> {

	@Autowired
	OmamekRepository	repository;


	@Override
	public Omamek convert(final String text) {
		Omamek result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
