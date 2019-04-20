package fr.unice.qna.persistence;

import javax.persistence.*;

@MappedSuperclass	
public class Post {

	private String content;
	private int upVotes;
	private int downVotes;

	@ManyToOne(optional=false)
	// @OneToOne(optional=false) causes primary key violation, @OneToOne does not
	private QnAUser author;


	public Post() {
		this("");
	}

	public Post(String content) {
		this.content = content;
	}

	public Post(String content, QnAUser author) {
		this.content = content;
		this.author = author;
	}

	public Post(String content, int upVotes, int downVotes) {
		this.content = content;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}

	public int getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}

	public QnAUser getAuthor() {
		return author;
	}

	public void setAuthor(QnAUser author) {
		this.author = author;
	}

	public int hashCode() {
		int res = content.hashCode() + upVotes + downVotes;
		res += (author==null)?0:author.hashCode();
		return res;
	}

	public boolean equals(Object o) {
		if(o == null) { return false; }
		if(o instanceof Post) {
			Post p = (Post)o;
			return content.equals(p.content) && upVotes == p.upVotes && downVotes == p.downVotes
				&& author.equals(p.author);
		}
		return false;
	}

	public String toString() {
		return String.format("Post[content: %s, upVotes: %d, downVotes: %d]", content, upVotes, downVotes);
	}

	public int getRating() {
		return upVotes - downVotes;
	}

	public void upVote() {
		++upVotes;
	}

	public void downVote() {
		++downVotes;
	}
}