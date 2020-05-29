package com.unicorn.studio.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
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
	
	@Column(name="type")
	@NotNull
	@Size(min=3, max=32)
	private String type;
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private Company company;
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private Investor investor;
	
	public User() {}

	public User(String firstName, String lastName, String email, String type) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
