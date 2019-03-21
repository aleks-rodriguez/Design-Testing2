
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Coordinate;

@Component
@Transactional
public class CoordinateToStringConverter implements Converter<Coordinate, String> {

	@Override
	public String convert(final Coordinate source) {
		String result;
		StringBuilder builder;
		if (source == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(String.valueOf(source.getLatitude()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(String.valueOf(source.getLongitude()), "UTF-8"));

				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
