package com.unicorn.studio.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="funding")
public class Funding {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="amount")
	@NotNull
	private int amount;
	
	@Column(name="stage")
	@NotNull
	@Size(min=3, max=32)
	private String stage;

	@Column(name="currency")
	private String currency;

	@Column(name="equity")
	private double equity;

	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="investor_id")
	private Investor investor;
	
	public Funding() {}

	public Funding(@NotNull int amount, @NotNull @Size(min = 3, max = 32) String stage, String currency, double equity) {
		this.amount = amount;
		this.stage = stage;
		this.currency = currency;
		this.equity = equity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getEquity() {
		return equity;
	}

	public void setEquity(double equity) {
		this.equity = equity;
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
		return "Funding{" +
				"id=" + id +
				", amount=" + amount +
				", stage='" + stage + '\'' +
				", currency='" + currency + '\'' +
				", equity=" + equity +
				", company=" + company +
				", investor=" + investor +
				'}';
	}
}
