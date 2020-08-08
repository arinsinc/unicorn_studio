package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="investor")
public class Investor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="fund")
	@NotNull
	private int fund;
	
	@Column(name="invested")
	@NotNull
	private int invested;

	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	public Investor() {}

	public Investor(int fund, int invested) {
		this.fund = fund;
		this.invested = invested;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFund() {
		return fund;
	}

	public void setFund(int fund) {
		this.fund = fund;
	}

	public int getInvested() {
		return invested;
	}

	public void setInvested(int invested) {
		this.invested = invested;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Investor [id=" + id + ", fund=" + fund + ", invested=" + invested + "]";
	}
	
}
