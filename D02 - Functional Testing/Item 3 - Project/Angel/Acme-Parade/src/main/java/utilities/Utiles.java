
package utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import security.Authority;
import domain.Box;
import domain.MessageEntity;

public class Utiles {

	public static Collection<String>	spamWords	= new ArrayList<String>();
	public static Collection<String>	goodWords	= new ArrayList<String>();
	public static Collection<String>	badWords	= new ArrayList<String>();
	public static Collection<String>	priorities	= new HashSet<String>();

	public static Integer				hoursFinder;
	public static Integer				resultsFinder;
	public static Integer				phonePrefix;

	public static String				systemName;
	public static String				banner;
	public static String				mess;


	public static void main(final String[] args) {
		final String aux1 = "190316-AML76L";
		final String aux2 = "190316-V53CAA";

		final List<String> tickers = Arrays.asList(aux1, aux2);
		System.out.println(tickers);
	}

	public static Collection<Box> initBoxes() {

		List<Box> boxesSystem;

		boxesSystem = new ArrayList<Box>();

		boxesSystem.add(Utiles.createBox(true, "In Box"));
		boxesSystem.add(Utiles.createBox(true, "Out Box"));
		boxesSystem.add(Utiles.createBox(true, "Spam Box"));
		boxesSystem.add(Utiles.createBox(true, "Notification Box"));
		boxesSystem.add(Utiles.createBox(true, "Trash Box"));

		return boxesSystem;

	}
	public static Box createBox(final boolean fromSystem, final String name) {

		Box b;

		b = new Box();

		b.setMessageEntity(new ArrayList<MessageEntity>());

		b.setFromSystem(fromSystem);

		b.setName(name);

		return b;

	}

	public static Collection<String> limpiaString(String s) {
		s = s.replaceAll("[^a-zA-Z0-9$]", "#");

		final List<String> textoRoto = Arrays.asList(s.split("##|#"));
		return textoRoto;
	}
	public static boolean spamWord(final Collection<String> contentMessage) {
		boolean res = false;

		Map<String, Boolean> result;
		result = new HashMap<>();

		for (final String word : Utiles.spamWords)
			result.put(word, contentMessage.contains(word.toLowerCase()));

		for (final Boolean b : result.values())
			if (b) {
				res = true;
				break;
			}

		return res;
	}

	public static String buildUrl(final Map<String, String> requestParams) {
		String s = "";
		if (requestParams.size() > 1)
			for (int i = requestParams.size() - 1; i >= 0; i--) {
				final String key = (String) requestParams.keySet().toArray()[i];
				s = s + key + "=" + requestParams.get(key) + "&";
			}
		else
			s = s + requestParams.keySet().toArray()[0] + "=" + requestParams.values().toArray()[0] + "&";
		return s.substring(0, s.length() - 1);
	}

	public static boolean checkURL(final Collection<String> urls) {
		boolean res = false;

		for (final String url : urls) {
			res = url.startsWith("http://") || url.startsWith("https://");
			if (res == false)
				break;
		}

		return res;
	}

	public static String hashPassword(final String old) {
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		String passEncoded;
		passEncoded = encoder.encodePassword(old, null);

		return passEncoded;
	}

	public static String generateTicker(final Collection<String> tickers) {
		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyMMdd");

		String formated;

		formated = formato.format(new Date());

		final Character[] ch = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};

		String c = "";

		Random random;

		random = new Random();

		for (int i = 0; i < 6; i++)
			c += ch[random.nextInt(ch.length)];

		String result;
		result = formated + "-" + c;

		if (tickers.contains(result))
			Utiles.generateTicker(tickers);

		return result;
	}
	public static Set<String> statusParadeByLang(final String s) {
		Map<String, Set<String>> result;
		result = new HashMap<String, Set<String>>();

		result.put("en", new HashSet<>(Arrays.asList("SUBMITTED", "ACCEPTED", "REJECTED")));
		result.put("es", new HashSet<>(Arrays.asList("ENVIADO", "ACEPTADO", "RECHAZADO")));

		return result.get(s);
	}
	public static void setParameters(final String systemName, final String banner, final String mess, final Integer hours, final Integer results, final Integer phonePrefix) {
		Utiles.systemName = systemName;
		Utiles.banner = banner;
		Utiles.mess = mess;
		Utiles.hoursFinder = hours;
		Utiles.resultsFinder = results;
		Utiles.phonePrefix = phonePrefix;
	}
	public static Boolean findAuthority(final Collection<Authority> comp, final String a) {
		Boolean res = false;
		if (comp.size() > 1) {
			Authority aut;
			aut = new Authority();
			aut.setAuthority(a);
			res = comp.contains(aut);
		} else
			for (final Authority authority : comp)
				if (authority.toString().equals(a))
					res = true;

		return res;
	}
	public static boolean isSpammer(final Collection<MessageEntity> cm) {
		int i = 0;
		for (final MessageEntity message : cm) {
			final boolean spam = Utiles.spamWord(Utiles.limpiaString(message.getSubject())) && Utiles.spamWord(Utiles.limpiaString(message.getBody()));
			if (spam)
				i++;
		}
		return (i / cm.size()) >= 0.1;
	}
	public static List<String> optimPosition(final List<String> queryResult) {
		List<String> res;
		res = new ArrayList<>();

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < queryResult.size(); j++) {
				String f;
				f = "" + i + "," + j;
				res.add(f);
			}
		res.removeAll(queryResult);
		return res;
	}
}
