
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Pelf;

@Component
@Transactional
public class PelfToStringConverter implements Converter<Pelf, String> {

	@Override
	public String convert(final Pelf q) {
		String result;

		if (q == null)
			result = null;
		else
			result = String.valueOf(q.getId());

		return result;
	}
}
