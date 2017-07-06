package com.web.app.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "orders")
public class Order {

	private Integer id;
	@Size(min = 3,max=15, message = "First name must be 3-15 characters long" )
	private String firstName;
	@Size(min = 3,max=15,  message = "Last name must be 3-15 characters long")
	private String lastName;
	@Size(min = 5,max=50,  message = "Address must be 5-50 characters long")
	private String address;
	@Min(value = 1, message = "Quantity cant be negative or zero")
	@Max(value = 99, message = "Quantity must be less than 99")
	private Integer quantity;
	private String sessionId;
	private User user;
	private Book book;
	private Date createdAt;

	public Order() {
	}

	public Order(String firstName, String lastName, String address, Integer quantity, String sessionId, Book book) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.quantity = quantity;
		this.sessionId = sessionId;
		this.book = book;
	}

	public Order(Order order) {
		this.firstName = order.firstName;
		this.lastName = order.lastName;
		this.address = order.address;
		this.quantity = order.quantity;
		this.sessionId = order.sessionId;
		this.book = order.book;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "session_id")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Column(name = "created_at")
	@CreationTimestamp
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Transient
	public String getShortDate() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		return dateFormatter.format(createdAt);
	}

	@Transient
	public String getTotal() {
		return  new DecimalFormat("#0.00").format(book.getPrice() * quantity);
	}

	@Override
	public String toString() {
		return String.format("First name: %s, last name: %s, address: %s, quantity: %s, session id: %s", firstName,
				lastName, address, quantity, sessionId);
	}
}
