
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Message;

public class MessageToStringConverter implements Converter<Message, String> {

	@Override
	public String convert(final Message mess) {
		String result;

		if (mess == null)
			result = null;
		else
			result = String.valueOf(mess.getId());

		return result;
	}

}
