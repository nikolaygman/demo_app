package com.web.app.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

	private Integer id;
	private String title;
	private String description;
	private String img_url;
	private Double price;
	private Set<Genre> genres = new LinkedHashSet<>();
	private Set<Author> authors = new LinkedHashSet<>();

	public Book() {

	}

	public Book(String title, String description, String img_url, Double price, Set<Genre> genres, Set<Author> authors) {
		this.title = title;
		this.description = description;
		this.img_url = img_url;
		this.price = price;
		this.genres = genres;
		this.authors = authors;
	}

	@Transient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "book_genre", joinColumns = {@JoinColumn(name = "book_title")}, inverseJoinColumns = {@JoinColumn(name = "genre")})
	@OrderBy("name ASC")
	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "book_author", joinColumns = {@JoinColumn(name = "book_title")}, inverseJoinColumns = {@JoinColumn(name = "author")})
	@OrderBy("name ASC")
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public void addAuthor(Author author) {
		this.authors.add(author);
	}

	public void addGenre(Genre genre) {
		this.genres.add(genre);
	}

	@Override
	public String toString() {
		return String.format("title: %s, description: %s, price: %s", title, description, price);
	}
}
