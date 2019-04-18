package fr.unice.qna.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Tag implements Comparable<Tag>, Serializable {

	@Id
	private String name;

	@ManyToMany
	private Set<Question> associatedQuestions = new HashSet<Question>(); 

	public Tag() {
		this("");
	}

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Question> getAssociatedQuestions() {
		return associatedQuestions;
	}

	public void setAssociatedQuestions(Set<Question> associatedQuestions) {
		this.associatedQuestions = associatedQuestions;
	}


	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) { return false; }
		if(o instanceof Tag) {
			Tag t = (Tag)o;
			return t.name.equals(name) && associatedQuestions.equals(t.associatedQuestions);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Tag[name: " + name+"]";
	}

	@Override
	public int compareTo(Tag t) {
		return name.compareTo(t.name);
	}
}