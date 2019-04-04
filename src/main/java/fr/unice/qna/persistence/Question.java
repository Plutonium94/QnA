package fr.unice.qna.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Question implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String detail;
	private long timestamp;

	private static final long serialVersionUID = 1L;

	public Question() {
		this.timestamp = System.nanoTime();
	}

	public Question(String title, String detail, long timestamp) {
		this.id = 0;
		this.title = title;
		this.detail = detail;
		this.timestamp = timestamp;
	}

	public Question(String title, String detail) {
		this(title, detail, System.nanoTime());
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
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail; 
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return String.format("Question[id=%d, title=%s, detail=%s, timestamp=%s]", id, title, detail, timestamp);
	}

	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Question)) { return false; }
		Question q = (Question)o;
		return id == q.id && title.equals(q.title) && detail.equals(detail) && timestamp==q.timestamp;
	}

	@Override
	public int hashCode() {
		return (int)id + title.hashCode() + detail.hashCode() + (int)timestamp;
	}
};