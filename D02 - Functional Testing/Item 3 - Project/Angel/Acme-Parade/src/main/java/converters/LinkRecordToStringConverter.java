
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LinkRecord;

@Component
@Transactional
public class LinkRecordToStringConverter implements Converter<LinkRecord, String> {

	@Override
	public String convert(final LinkRecord link) {
		String result;

		if (link == null)
			result = null;
		else
			result = String.valueOf(link.getId());

		return result;
	}
}
