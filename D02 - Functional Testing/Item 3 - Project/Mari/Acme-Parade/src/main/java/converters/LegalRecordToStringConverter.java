
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.LegalRecord;

public class LegalRecordToStringConverter implements Converter<LegalRecord, String> {

	@Override
	public String convert(final LegalRecord legal) {
		String result;

		if (legal == null)
			result = null;
		else
			result = String.valueOf(legal.getId());

		return result;
	}

}
