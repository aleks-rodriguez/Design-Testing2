
package forms;

import java.util.Collection;
import java.util.Date;

import domain.Actor;

public class MessageForm {

	private Actor				sender;
	private Collection<Actor>	receiver;
	private Date				momentsent;
	private String				subject;
	private String				body;
	private Collection<String>	tags;
	private String				priority;
	private int					id;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public Collection<Actor> getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Collection<Actor> receiver) {
		this.receiver = receiver;
	}

	public Date getMomentsent() {
		return this.momentsent;
	}

	public void setMomentsent(final Date momentsent) {
		this.momentsent = momentsent;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

}
