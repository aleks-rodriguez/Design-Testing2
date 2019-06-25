
package ticketable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mifmif.common.regex.Generex;

@Service
public class TickerServiceInter {

	@Autowired
	private TickerService	service;


	public Ticker create(final String first, final String pattern) {

		Ticker ticker;
		ticker = new Ticker();
		ticker.setTicker(first + "-" + new Generex(pattern).random().toUpperCase());
		return ticker;
	}
	public Ticker create(final String pattern, final String delimiter, final String second) {

		Ticker ticker;
		ticker = new Ticker();
		ticker.setTicker(new Generex(pattern).random().toUpperCase() + delimiter + second);
		return ticker;
	}
	public <K extends Ticketable> K withTicker(final K without, final GenericRepository repository, final String first, final String pattern, final boolean firstOrSecond) {

		K result = null;

		Ticker aux = null;

		boolean value = false;

		do
			try {

				if (without.getId() != 0) {

					K auxFromDB;
					auxFromDB = (K) repository.findOne(without.getId());
					System.out.println(auxFromDB.getTicker());
					boolean check;
					check = auxFromDB.getTicker().getTicker().equals(without.getTicker().getTicker());

					if (!check)
						without.setTicker(auxFromDB.getTicker());

				}

				if (without.getId() == 0) {
					Ticker findByCode;
					findByCode = repository.findTickerByCode(without.getTicker().getTicker());

					if (findByCode != null)
						throw new IllegalArgumentException();
					else {
						findByCode = this.service.saveTicker(without.getTicker());
						without.setTicker(findByCode);
					}
				}

				result = (K) repository.save(without);
				value = true;
			} catch (final Throwable oops) {
				value = false;
				if (firstOrSecond)
					aux = this.create(first, pattern);
				else
					aux = this.create(pattern, ":", first);
				without.setTicker(aux);
			}
		while (value == false);

		return result;
	}
	public void deleteTicker(final int id) {
		this.service.deleteTicker(id);
	}
}
