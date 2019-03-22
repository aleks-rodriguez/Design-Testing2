
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import security.Authority;
import domain.Box;
import domain.CreditCard;
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
		final List<String> res = new ArrayList<String>(Arrays.asList("1", "2"));
		System.out.println(res.toString().substring(1, res.toString().length() - 1));
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

	public static String generateTicker() {
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

		return formated + "-" + c;
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

	public static CreditCard createCreditCard() {
		CreditCard creditCard;
		creditCard = new CreditCard();
		creditCard.setHolder("");
		creditCard.setMake("");
		creditCard.setNumber("");
		creditCard.setExpiration(new Date());
		creditCard.setCvv(0);

		return creditCard;
	}

	public static String[] checkCreditCard(final String cadena) {
		/**
		 * This method is implemented according to the Luhn Algorithm
		 * https://www.journaldev.com/1443/java-credit-card-validation-luhn-algorithm-java
		 * https://howtodoinjava.com/regex/java-regex-validate-credit-card-numbers/
		 */

		/**
		 * This string array only have two values
		 * 1. Type of creditCard
		 * 2. It is a legal credit card or none.
		 */
		String[] result;
		result = new String[2];

		/**
		 * This makes a little repository of patterns that checks directly which kind of credit card is given by cadena´s parameter.
		 */
		Map<String, Matcher> map;
		map = new HashMap<String, Matcher>();

		map.put("VISA", Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$").matcher(cadena));
		map.put("MASTERCARD", Pattern.compile("^5[1-5][0-9]{14}$").matcher(cadena));
		map.put("AMEX", Pattern.compile("^3[47][0-9]{13}$").matcher(cadena));
		map.put("DINERS", Pattern.compile("^3(?:0[0-5]|[68][0-9])?[0-9]{11}$").matcher(cadena));

		if (map.get("VISA").matches())
			result[0] = "VISA";
		else if (map.get("MASTERCARD").matches())
			result[0] = "MASTERCARD";
		else if (map.get("AMEX").matches())
			result[0] = "AMEX";
		else if (map.get("DINERS").matches())
			result[0] = "DINERS";

		/**
		 * Luhn Algorithm
		 */
		result[1] = Boolean.toString(Utiles.luhnAlgorithm(cadena));

		return result;

	}
	private static boolean luhnAlgorithm(final String cadena) {
		final int[] str = new int[cadena.length()];

		for (int i = 0; i < str.length; i++)
			str[i] = Integer.parseInt(cadena.substring(i, i + 1));

		for (int i = str.length - 2; i >= 0; i = i - 2) {

			int j = str[i];
			j = j * 2;

			if (j > 9)
				j = j % 10 + 1;

			str[i] = j;

		}
		int sum = 0;

		for (int i = 0; i < str.length; i++)
			sum = sum + str[i];

		return sum % 10 == 0;
	}
}
