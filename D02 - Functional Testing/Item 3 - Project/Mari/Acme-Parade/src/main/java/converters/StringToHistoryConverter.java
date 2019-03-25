
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HistoryRepository;
import domain.History;

@Component
@Transactional
public class StringToHistoryConverter implements Converter<String, History> {

	@Autowired
	HistoryRepository	historyRepository;


	@Override
	public History convert(final String text) {
		History result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				System.out.println(id);
				result = this.historyRepository.findOne(id);
			}
			System.out.println(result);
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}
}
