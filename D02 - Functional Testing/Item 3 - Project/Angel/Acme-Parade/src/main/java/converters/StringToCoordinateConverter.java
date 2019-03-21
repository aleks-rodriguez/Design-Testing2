
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Coordinate;

@Component
@Transactional
public class StringToCoordinateConverter implements Converter<String, Coordinate> {

	@Override
	public Coordinate convert(final String source) {
		Coordinate result;
		String[] parts;
		if (source == null)
			result = null;
		else
			try {
				parts = source.split("\\|");
				result = new Coordinate();

				result.setLatitude(Double.valueOf(URLDecoder.decode(parts[0], "UTF-8")));
				result.setLongitude(Double.valueOf(URLDecoder.decode(parts[1], "UTF-8")));

			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
