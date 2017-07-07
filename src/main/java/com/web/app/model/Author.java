package com.web.app.model;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author {

	private Integer id;
	private String name;

	public Author() {
	}

	public Author(String name) {
		this.name = name;
	}

	@Transient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("[name: %s]", name);
	}

}
