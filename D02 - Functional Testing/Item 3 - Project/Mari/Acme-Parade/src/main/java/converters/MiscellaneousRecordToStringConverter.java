
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousRecord;

@Component
@Transactional
public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord, String> {

	@Override
	public String convert(final MiscellaneousRecord miscellaneous) {
		String result;

		if (miscellaneous == null)
			result = null;
		else
			result = String.valueOf(miscellaneous.getId());

		return result;
	}

}
