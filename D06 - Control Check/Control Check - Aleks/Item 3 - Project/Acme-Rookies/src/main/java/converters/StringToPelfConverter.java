
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PelfRepository;
import domain.Pelf;

@Component
@Transactional
public class StringToPelfConverter implements Converter<String, Pelf> {

	@Autowired
	PelfRepository	pelfRepository;


	@Override
	public Pelf convert(final String text) {
		Pelf result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.pelfRepository.findOne(id);
			}
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
