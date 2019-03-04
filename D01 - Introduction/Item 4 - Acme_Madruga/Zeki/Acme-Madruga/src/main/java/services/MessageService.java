
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private Validator			validator;

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

	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}
	//Create
	public Message createMessage(final Actor a) {
		Message message;
		message = new Message();

		message.setSender(a);
		message.setBody("");
		message.setMomentsent(new Date());
		message.setTags(new ArrayList<String>());
		message.setSubject("");
		message.setPriority("NEUTRAL");
		message.setBox(new ArrayList<Box>());
		message.setReceiver(new ArrayList<Actor>());

		return message;
	}
	//Save
	public Message sendMessage(final Message mess) {
		Message saved;
		saved = this.messageRepository.save(mess);
		//Send message
		final Actor sender;
		sender = mess.getSender();

		// to do the message of the sistem --------
		Box outBox;
		if (mess.getTags().contains("Application"))
			outBox = this.boxService.getActorEntryBox(sender.getId());
		else
			outBox = this.boxService.getActorSendedBox(sender.getId());
		//-----------------------------------------

		Collection<Message> collMessage;
		collMessage = outBox.getMessage();
		collMessage.add(saved);
		outBox.setMessage(collMessage);

		Collection<Box> colBox;
		colBox = saved.getBox();
		colBox.add(outBox);
		saved.setBox(colBox);

		//spam in message
		//		final boolean spam = Utiles.spamWord(Utiles.limpiaString(result.getSubject())) && Utiles.spamWord(Utiles.limpiaString(result.getBody()));
		this.received(saved);
		//		sender.setSuspicious(spam);

		return saved;

	}

	public void received(final Message saved/* , final Boolean spam */) {

		final Collection<Actor> recipients;
		recipients = saved.getReceiver();
		final int userlogged;
		userlogged = LoginService.getPrincipal().getId();

		Collection<Box> boxes;

		//		if (spam)
		//			boxes = this.messageRepository.getBoxesFromActors("Spam Box", recipients, userlogged);
		//		else
		boxes = this.messageRepository.getBoxesFromActors("In Box", recipients, userlogged);

		Collection<Box> boxesMessage;
		boxesMessage = saved.getBox();
		boxesMessage.addAll(boxes);
		saved.setBox(boxesMessage);

		for (final Box box : boxesMessage) {

			Collection<Message> messagesInBox;
			messagesInBox = box.getMessage();
			messagesInBox.add(saved);
			box.setMessage(messagesInBox);
		}
	}

	public Integer moveTo(final String boxCase, final Message mess) {

		final Actor a = this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId());

		Collection<Box> mesageBoxes;
		mesageBoxes = mess.getBox();

		Collection<Box> currentBoxes;
		currentBoxes = this.messageRepository.getBoxesFromActorAndMessage(mess.getId(), a.getId());
		Box box = null;

		if (boxCase.equals("In Box")) {
			box = this.boxService.getActorEntryBox(a.getId());
			mesageBoxes.removeAll(currentBoxes);
		} else if (this.boxService.findOne(Integer.valueOf(boxCase)).getName().equals("Trash Box")) {
			box = this.boxService.getActorTrashBox(a.getId());
			mesageBoxes.removeAll(currentBoxes);
		} else
			box = this.boxService.getActorOtherBox(a.getId(), Integer.valueOf(boxCase));

		Collection<Message> messa;
		messa = box.getMessage();

		messa.add(mess);
		box.setMessage(messa);

		mesageBoxes.add(box);
		mess.setBox(mesageBoxes);

		this.messageRepository.save(mess);

		return box.getId();
	}

	public void deleteMessage(final Message mesage) {
		final Actor a = this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId());

		Box b;
		b = this.boxService.getActorTrashBox(a.getId());

		Collection<Message> mess;
		mess = b.getMessage();

		Collection<Box> boxesMesage;
		boxesMesage = mesage.getBox();

		if (mess.contains(mesage)) {
			mess.remove(mesage);
			b.setMessage(mess);

			boxesMesage.remove(b);
			mesage.setBox(boxesMesage);
		}

	}

	public Message reconstruct(final Message message, final BindingResult binding) {

		Message result;

		if (message.getId() == 0) {
			result = this.createMessage(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()));
			result.setSubject(message.getSubject());
			result.setBody(message.getBody());
			result.setPriority(message.getPriority());
			result.setTags(message.getTags());
			result.setReceiver(new ArrayList<Actor>(message.getReceiver()).subList(1, message.getReceiver().size()));
		} else
			result = this.messageRepository.findOne(message.getId());

		this.validator.validate(result, binding);

		return result;

	}
}
