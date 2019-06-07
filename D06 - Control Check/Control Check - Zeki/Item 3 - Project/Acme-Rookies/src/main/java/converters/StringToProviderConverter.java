
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProviderRepository;
import domain.Provider;

@Component
@Transactional
public class StringToProviderConverter implements Converter<String, Provider> {

	@Autowired
	ProviderRepository	profileRepository;


	@Override
	public Provider convert(final String text) {
		Provider result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.profileRepository.findOne(id);
			}
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
