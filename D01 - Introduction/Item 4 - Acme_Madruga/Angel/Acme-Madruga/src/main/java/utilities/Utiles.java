
package utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import security.Authority;

public class Utiles {

	public static Collection<String>	spamWords	= new ArrayList<String>();
	public static Collection<String>	goodWords	= new ArrayList<String>();
	public static Collection<String>	badWords	= new ArrayList<String>();
	public static Collection<String>	priorities	= new ArrayList<String>();

	public static Integer				hoursFinder;
	public static Integer				resultsFinder;
	public static Integer				phonePrefix;

	public static String				systemName;
	public static String				banner;
	public static String				mess;


	public static void main(final String[] args) {
		final Collection<String> urls = Arrays.asList("https://", "http://");
		System.out.println(Utiles.checkURL(urls));
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
}
