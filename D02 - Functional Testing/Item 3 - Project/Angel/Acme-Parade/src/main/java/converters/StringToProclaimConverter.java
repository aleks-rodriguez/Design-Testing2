
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProclaimRepository;
import domain.Proclaim;

@Component
@Transactional
public class StringToProclaimConverter implements Converter<String, Proclaim> {

	@Autowired
	ProclaimRepository	proclaimRepository;


	@Override
	public Proclaim convert(final String text) {
		Proclaim result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				System.out.println(id);
				result = this.proclaimRepository.findOne(id);
			}
			System.out.println(result);
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
