package com.unicorn.studio.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	private int id;
	
	@Column(name="fund")
	@NotNull
	private int fund;
	
	@Column(name="invested")
	@NotNull
	private int invested;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="investor", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Funding> funding;
	
	public Investor() {}

	public Investor(int fund, int invested, User user) {
		this.fund = fund;
		this.invested = invested;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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


	@Override
	public String toString() {
		return "Investor [id=" + id + ", fund=" + fund + ", invested=" + invested + ", user=" + user + "]";
	}
	
}
