
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.RequestRepository;
import domain.Request;

@Component
@Transactional
public class StringToRequestConverter implements Converter<String, Request> {

	@Autowired
	private RequestRepository	reqRepo;


	@Override
	public Request convert(final String source) {
		Request result;
		int id;
		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.reqRepo.findOne(id);
			}
		} catch (final Throwable opps) {
			throw new IllegalArgumentException(opps);
		}
		return result;
	}

}
