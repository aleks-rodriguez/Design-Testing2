
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Omamek;

@Component
@Transactional
public class OmamekToStringConverter implements Converter<Omamek, String> {

	@Override
	public String convert(final Omamek omamek) {
		String result;

		if (omamek == null)
			result = null;
		else
			result = String.valueOf(omamek.getId());

		return result;
	}
}
