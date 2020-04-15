package com.unicorn.studio.entity;

import java.sql.Date;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="company")
public class Company {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	@NotNull
	@Size(min=3, max=32)
	private String name;
	
	@Column(name="headquarter")
	@NotNull
	@Size(min=3, max=32)
	private String headQuarter;
	
	@Column(name="founded_year")
	@NotNull
	private Date foundedYear;
	
	@Column(name="industry")
	@NotNull
	@Size(min=3, max=32)
	private String industry;
	
	@Column(name="type")
	@NotNull
	@Size(min=3, max=32)
	private String type;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="company", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Funding> fundings;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="company_club",
			joinColumns=@JoinColumn(name="company_id"),
			inverseJoinColumns=@JoinColumn(name="club_id"))
	private List<Club> clubs;
	
	public Company() {}

	public Company(String name, String headQuarter, Date foundedYear, String industry, String type) {
		this.name = name;
		this.headQuarter = headQuarter;
		this.foundedYear = foundedYear;
		this.industry = industry;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadQuarter() {
		return headQuarter;
	}

	public void setHeadQuarter(String hQuarter) {
		this.headQuarter = hQuarter;
	}

	public Date getFoundedYear() {
		return foundedYear;
	}

	public void setFoundedYear(Date foundedYear) {
		this.foundedYear = foundedYear;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Funding> getFundings() {
		return fundings;
	}

	public void setFundings(List<Funding> fundings) {
		this.fundings = fundings;
	}

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", headQuarter=" + headQuarter + ", foundedYear=" + foundedYear
				+ ", industry=" + industry + ", type=" + type + "]";
	}
	
	
}
