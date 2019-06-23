
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Aolet;

@Component
@Transactional
public class AoletToStringConverter implements Converter<Aolet, String> {

	@Override
	public String convert(final Aolet aolet) {
		String result;

		if (aolet == null)
			result = null;
		else
			result = String.valueOf(aolet.getId());

		return result;
	}
}
