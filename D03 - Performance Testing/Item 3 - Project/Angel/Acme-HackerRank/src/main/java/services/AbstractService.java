
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import security.Authority;
import domain.Actor;
import domain.Box;
import domain.MessageEntity;
import domain.Problem;

public class AbstractService {

	public String limpiaCadena(final String s) {
		return s.replaceAll("[^a-zA-Z0-9$]", "");
	}

	public Collection<String> limpiaString(final String s) {
		return Arrays.asList(s.replaceAll("[^a-zA-Z0-9$]", "#").split("##|#"));
	}
	public Collection<Box> initBoxes() {

		List<Box> boxesSystem;

		boxesSystem = new ArrayList<Box>();

		boxesSystem.add(this.createBox(true, "In Box"));
		boxesSystem.add(this.createBox(true, "Out Box"));
		boxesSystem.add(this.createBox(true, "Spam Box"));
		boxesSystem.add(this.createBox(true, "Notification Box"));
		boxesSystem.add(this.createBox(true, "Trash Box"));

		return boxesSystem;

	}
	public Box createBox(final boolean fromSystem, final String name) {

		Box b;

		b = new Box();

		b.setMessageEntity(new ArrayList<MessageEntity>());

		b.setFromSystem(fromSystem);

		b.setName(name);

		return b;

	}
	public boolean spamWord(final Collection<String> contentMessage) {
		boolean res = false;

		Map<String, Boolean> result;
		result = new HashMap<>();

		for (final String word : System.getProperty("spamwords").split(","))
			result.put(word, contentMessage.contains(word.toLowerCase()));

		for (final Boolean b : result.values())
			if (b) {
				res = true;
				break;
			}

		return res;
	}

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
	private boolean isSpammer(final Collection<MessageEntity> cm) {
		boolean res = false;
		if (cm.size() != 0) {
			int i = 0;
			for (final MessageEntity message : cm) {
				final boolean spam = this.spamWord(this.limpiaString(message.getSubject())) && this.spamWord(this.limpiaString(message.getBody()));
				if (spam)
					i++;
			}
			res = (i / cm.size()) >= 0.1;
		}
		return res;
	}
	public boolean checkSpammer(final Actor a) {
		boolean res;
		res = false;
		/**
		 * Se mantiene comentado por ahora puesto que Box le toca colocarlo a Zeki
		 */
		//		for (final Box b : a.getBoxes())
		//			if (b.getName().equals("Out Box"))
		//				res = this.isSpammer(b.getMessageEntity());
		return res;
	}

	public Problem lookingForProblem(final Collection<Problem> problems) {
		final ArrayList<Problem> col;
		col = (ArrayList<Problem>) problems;

		final Problem result;
		final Integer collectionSize = problems.size();

		Random random;
		random = new Random();
		final Integer number = random.nextInt(collectionSize);
		result = col.get(number);

		return result;
	}

}
