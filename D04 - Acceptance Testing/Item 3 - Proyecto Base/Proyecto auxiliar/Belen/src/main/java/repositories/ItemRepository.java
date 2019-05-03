
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Item;
import domain.Provider;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	Actor findActorByUserAccountId(int id);

	@Query("select i.provider from Item i where i.id = ?1")
	Provider showProviderByItem(int id);

	@Query("select i from Item i where i.provider.id = ?1")
	Collection<Item> getItemsByProvider(int id);

	@Query("select p from Provider p")
	Collection<Provider> findAllProviders();
}
