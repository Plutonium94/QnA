package fr.unice.qna.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Answer extends Post implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public Answer() {
	}

	public Answer(String content) {
		super(content);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return (int)id + super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) { return false; }
		if(o instanceof Answer) {
			Answer a = (Answer)o;
			return id == a.id && super.equals(a);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("Answer[id: %d, content: %s, upvotes: %d, downvotes: %d]",
			getId(), getContent(), getUpVotes(), getDownVotes());
	}
}