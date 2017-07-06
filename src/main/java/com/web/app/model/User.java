package com.web.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements UserDetails {

	private Integer id;
	@Size(min=4,message = "Name must be at least 4 characters long")
	private String username;
	@Size(min=4, message = "Password must be at least 4 characters long")
	private String password;
	private Set<Authority> authorities = new HashSet<Authority>(1);
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public User() {
	}

	public User(User user) {
		this.username = user.username;
		this.password = user.password;
		this.authorities = user.authorities;
	}
	public User(String username, String password, Set<Authority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	@Override
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	@Column(name = "user_name",unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return enabled;
	}
	@Override
	public String toString() {
		return String.format("[username: %s, password: %s]", username, password);
	}
}
