
package services;

import java.util.Arrays;
import java.util.Collection;

import security.Authority;

public class AbstractService {

	public String limpiaCadena(final String s) {
		return s.replaceAll("[^a-zA-Z0-9$]", "");
	}

	public Collection<String> limpiaString(final String s) {
		return Arrays.asList(s.replaceAll("[^a-zA-Z0-9$]", "#").split("##|#"));
	}
	//	public boolean spamWord(final Collection<String> contentMessage) {
	//		boolean res = false;
	//
	//		Map<String, Boolean> result;
	//		result = new HashMap<>();
	//
	//		for (final String word : spamWords)
	//			result.put(word, contentMessage.contains(word.toLowerCase()));
	//
	//		for (final Boolean b : result.values())
	//			if (b) {
	//				res = true;
	//				break;
	//			}
	//
	//		return res;
	//	}

	public Boolean findAuthority(final Collection<Authority> comp, final String a) {
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
