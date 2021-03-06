
package ticketable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Integer> {

	@Query("select t from Ticker t where t.ticker = ?1")
	Ticker findTickerByCode(String code);
}
