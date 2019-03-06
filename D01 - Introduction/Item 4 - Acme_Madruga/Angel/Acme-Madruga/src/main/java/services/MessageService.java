
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageEntityRepository;
import security.LoginService;
import utilities.Utiles;
import domain.Actor;
import domain.Box;
import domain.MessageEntity;

@Service
@Transactional
public class MessageService {

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private MessageEntityRepository	messageRepository;

	@Autowired
	private BoxService				boxService;


	//Queries del repository
	public Collection<MessageEntity> getMessagesByBox(final int id) {
		return this.messageRepository.getMessagesByBox(id);
	}

	public Collection<Box> getBoxesFromActorAndMessage(final int message, final int actor) {
		return this.messageRepository.getBoxesFromActorAndMessage(message, actor);
	}

	public Collection<Box> getBoxesFromActors(final String name, final Collection<Actor> actors, final int userlogged) {
		return this.messageRepository.getBoxesFromActors(name, actors, userlogged);
	}

	public MessageEntity findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Collection<Actor> getReceiver(final int id) {
		return this.messageRepository.getReceiver(id);
	}

	public void delete(final MessageEntity m) {
		this.messageRepository.delete(m);
	}

	public Collection<MessageEntity> getMessageOutBox(final int id) {
		return this.messageRepository.getMessagesOutBox(id);
	}

	//Create
	public MessageEntity createMessage(final Actor a) {
		MessageEntity message;
		message = new MessageEntity();

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
	public MessageEntity sendMessage(final MessageEntity mess) {
		MessageEntity saved;
		saved = this.messageRepository.save(mess);
		//Send message
		final Actor sender;
		sender = mess.getSender();

		// to do the message of the sistem --------
		Box outBox;
		//		if (mess.getTags().contains("Application"))
		//			outBox = this.boxService.getActorEntryBox(sender.getId());
		//		else
		outBox = this.boxService.getActorSendedBox(sender.getId());
		//-----------------------------------------

		Collection<MessageEntity> collMessage;
		collMessage = outBox.getMessageEntity();
		collMessage.add(saved);
		outBox.setMessageEntity(collMessage);

		Collection<Box> colBox;
		colBox = saved.getBox();
		colBox.add(outBox);
		saved.setBox(colBox);

		//spam in message
		final boolean spam = Utiles.spamWord(Utiles.limpiaString(saved.getSubject())) && Utiles.spamWord(Utiles.limpiaString(saved.getBody()));
		this.received(saved, spam);
		//		sender.setSuspicious(spam);

		return saved;

	}

	public void received(final MessageEntity saved, final Boolean spam) {

		final Collection<Actor> recipients;
		recipients = saved.getReceiver();
		final int userlogged;
		userlogged = LoginService.getPrincipal().getId();

		Collection<Box> boxes;

		if (spam)
			boxes = this.messageRepository.getBoxesFromActors("Spam Box", recipients, userlogged);
		else if (saved.getTags().contains("Notification"))
			boxes = this.messageRepository.getBoxesFromActors("Notification Box", recipients, userlogged);
		else
			boxes = this.messageRepository.getBoxesFromActors("In Box", recipients, userlogged);
		Collection<Box> boxesMessage;
		boxesMessage = saved.getBox();
		boxesMessage.addAll(boxes);
		saved.setBox(boxesMessage);

		for (final Box box : boxesMessage) {

			Collection<MessageEntity> messagesInBox;
			messagesInBox = box.getMessageEntity();
			messagesInBox.add(saved);
			box.setMessageEntity(messagesInBox);
		}
	}

	public Integer moveTo(final String boxCase, final MessageEntity mess) {

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

		Collection<MessageEntity> messa;
		messa = box.getMessageEntity();

		messa.add(mess);
		box.setMessageEntity(messa);

		mesageBoxes.add(box);
		mess.setBox(mesageBoxes);

		this.messageRepository.save(mess);

		return box.getId();
	}

	public void deleteMessage(final MessageEntity mesage) {
		final Actor a = this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId());

		Box b;
		b = this.boxService.getActorTrashBox(a.getId());

		Collection<MessageEntity> mess;
		mess = b.getMessageEntity();

		Collection<Box> boxesMesage;
		boxesMesage = mesage.getBox();

		if (mess.contains(mesage)) {
			mess.remove(mesage);
			b.setMessageEntity(mess);

			boxesMesage.remove(b);
			mesage.setBox(boxesMesage);
		}

		Collection<Actor> col;
		col = this.messageRepository.getReceiver(mesage.getId());

		if (col.contains(a)) {
			Collection<Actor> act;
			act = col;
			act.remove(a);
			mesage.setReceiver(act);
		}
		//
		//		if (mesage.getSender().getId() == a.getId())
		//			if (mesage.getReceiver().equals(null))
		//				this.messageRepository.delete(mesage);

		//		if (mesage.getSender().getId() == a.getId())
		//			mesage.setSender(Actor.this);
		//
		//		if (mesage.getReceiver() == null && mesage.getSender() == null)
		//			this.messageRepository.delete(mesage);

	}

	public MessageEntity reconstruct(final MessageEntity message, final BindingResult binding) {

		MessageEntity result;

		if (message.getId() == 0) {
			result = this.createMessage(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()));
			result.setSubject(message.getSubject());
			result.setBody(message.getBody());
			result.setPriority(message.getPriority());
			result.setTags(message.getTags());
			if (message.getReceiver() != null) {
				if (message.getReceiver().contains(null))
					result.setReceiver(new ArrayList<Actor>(message.getReceiver()).subList(1, message.getReceiver().size()));
				else
					result.setReceiver(message.getReceiver());
			} else
				result.setReceiver(message.getReceiver());
		} else {
			result = this.messageRepository.findOne(message.getId());
			result.setSubject(message.getSubject());
			result.setBody(message.getBody());
			result.setPriority(message.getPriority());
			result.setTags(message.getTags());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;

	}

}
