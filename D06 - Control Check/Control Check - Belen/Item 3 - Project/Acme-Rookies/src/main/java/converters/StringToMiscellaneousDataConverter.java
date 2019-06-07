
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData> {

	@Autowired
	MiscellaneousDataRepository	profileRepository;


	@Override
	public MiscellaneousData convert(final String text) {
		MiscellaneousData result;
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
