package fr.unice.qna.persistence;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Question extends Post implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private long timestamp;

	@OneToOne
	private Answer acceptedAnswer;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Tag> tags = new TreeSet<Tag>();

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Answer> answers = new ArrayList<Answer>();

	private static final long serialVersionUID = 1L;

	public Question() {
		this.timestamp = System.nanoTime();
	}

	public Question(String title, String detail, long timestamp) {
		super(detail);
		this.title = title;
		this.timestamp = timestamp;
	}


	public Question(String title, String detail) {
		this(title, detail, System.nanoTime());
	}

	public Question(String title, String detail, String... tagNames) {
		this(title, detail);
		for(String tn : tagNames) {
			this.tags.add(new Tag(tn));
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return super.getContent();
	}

	public void setDetail(String detail) {
		super.setContent(detail); 
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Answer getAcceptedAnswer() {
		return acceptedAnswer;
	}

	public void setAcceptedAnswer(Answer acceptedAnswer) {
		this.acceptedAnswer = acceptedAnswer;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String toString() {
		return String.format("Question[id=%d, title=%s, detail=%s, timestamp=%d, tags=%s, acceptedAnswer=%s, answers=%s]", id, title, getDetail(), timestamp, tags.toString(), acceptedAnswer, answers.toString());
	}

	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Question)) { return false; }
		Question q = (Question)o;
		return id == q.id && title.equals(q.title) && timestamp==q.timestamp 
			&& tags.equals(q.tags) && acceptedAnswer.equals(q.acceptedAnswer)
			&& answers.equals(q.answers) 
			&& super.equals(o);
	}

	@Override
	public int hashCode() {
		return (int)id + title.hashCode() + (int)timestamp + 
			tags.hashCode() + acceptedAnswer.hashCode() + answers.hashCode() +
			super.hashCode();
	}
}