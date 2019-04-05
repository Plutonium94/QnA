package fr.unice.qna.persistence;

import javax.persistence.*;

@MappedSuperclass	
public class Post {

	private String content;
	private int upVotes;
	private int downVotes;

	public Post() {

	}

	public Post(String content) {
		this.content = content;
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

	public int hashCode() {
		return content.hashCode() + upVotes + downVotes;
	}

	public boolean equals(Object o) {
		if(o instanceof Post) {
			Post p = (Post)o;
			return content.equals(p.content) && upVotes == p.upVotes && downVotes == p.downVotes;
		}
		return false;
	}

	public String toString() {
		return String.format("Post[content: %s, upVotes: %d, downVotes: %d]", content, upVotes, downVotes);
	}
}