package fr.unice.qna.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Tag implements Comparable<Tag>, Serializable {

	@Id
	private String name;

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


	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Tag) {
			Tag t = (Tag)o;
			return t.name.equals(name);
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