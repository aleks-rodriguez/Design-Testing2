
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Request;

@Component
public class RequestToStringConverter implements Converter<Request, String> {

	@Override
	public String convert(final Request req) {
		String result;

		if (req == null)
			result = null;
		else
			result = String.valueOf(req.getId());
		return result;
	}

}
