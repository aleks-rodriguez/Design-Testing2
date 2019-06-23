
package ticketable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<K extends Ticketable> extends JpaRepository<K, Integer> {

	@Query("select t from Ticker t where t.ticker = ?1")
	Ticker findTickerByCode(String code);
}
