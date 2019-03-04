
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Box;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	// Get messages de la caja
	@Query("select b.message from Box b where b.id = ?1")
	Collection<Message> getMessagesByBox(int id);

	// Get boxes de un actor
	@Query("select b from Actor a join a.boxes b join b.message m where m.id = ?1 and a.id = ?2")
	Collection<Box> getBoxesFromActorAndMessage(int message, int actor);

	@Query("select b from Actor a join a.boxes b where b.name = ?1 and a IN ?2 and a.account.id <> ?3")
	Collection<Box> getBoxesFromActors(String name, Collection<Actor> actors, int userlogged);

	@Query("select m.receiver from Message m where m.id = ?1 ")
	Collection<Actor> getReceiver(int id);

	@Query("select m from Actor a join a.boxes b join b.message m where b.name = 'Out Box' AND a.id = ?1")
	Collection<Message> getMessagesOutBox(int id);

}
