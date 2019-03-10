
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AreaRepository;
import domain.Area;

@Component
@Transactional
public class StringToAreaConverter implements Converter<String, Area> {

	@Autowired
	private AreaRepository	areaRepository;


	@Override
	public Area convert(final String text) {
		Area result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				if (id == 0)
					result = new Area();
				else
					result = this.areaRepository.findOne(id);
			}
			System.out.println(result);
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}

}
