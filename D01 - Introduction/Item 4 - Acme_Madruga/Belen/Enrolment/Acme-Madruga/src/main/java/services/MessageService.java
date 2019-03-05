
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private BoxService			boxService;


	//Queries del repository
	public Collection<Message> getMessagesByBox(final int id) {
		return this.messageRepository.getMessagesByBox(id);
	}

	public Collection<Box> getBoxesFromActorAndMessage(final int message, final int actor) {
		return this.messageRepository.getBoxesFromActorAndMessage(message, actor);
	}

	public Collection<Box> getBoxesFromActors(final String name, final Collection<Actor> actors, final int userlogged) {
		return this.messageRepository.getBoxesFromActors(name, actors, userlogged);
	}

}
