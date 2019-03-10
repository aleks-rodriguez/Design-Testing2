
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Parade;

@Component
@Transactional
public class ParadeToStringConverter implements Converter<Parade, String> {

	@Override
	public String convert(final Parade procession) {
		String result;

		if (procession == null)
			result = null;
		else
			result = String.valueOf(procession.getId());

		return result;
	}
}
