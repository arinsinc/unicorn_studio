package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="first_name")
	@NotNull
	@Size(min=3, max=32)
	private String firstName;
	
	@Column(name="last_name")
	@NotNull
	@Size(min=3, max=32)
	private String lastName;
	
	@Column(name="email")
	@NotNull
	@Size(min=3, max=32)
	@Email
	private String email;

	@Column(name="password")
	@NotNull
	private String password;
	
	@Column(name="type")
	@NotNull
	@Size(min=3, max=32)
	private String type;

	@Column(name="last_login")
	private Date last_login;

	@Column(name="enabled")
	private Boolean enabled = true;

	@JsonIgnore
	@OneToOne(mappedBy="user", fetch= FetchType.LAZY, cascade=CascadeType.ALL)
	private Company company;

	@JsonIgnore
	@OneToOne(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Investor investor;
	
	public User() {}

	public User(String firstName, String lastName, String email, String password, String type) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() { return password; }

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", type="
				+ type + "]";
	}
	
	
}
