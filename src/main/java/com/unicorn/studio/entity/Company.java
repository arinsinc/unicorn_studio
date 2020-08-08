package com.unicorn.studio.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private long id;
	
	@Column(name="company_name")
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
	
	@Column(name="company_type")
	@NotNull
	@Size(min=3, max=32)
	private String type;

	@Column(name="headline")
	@NotNull
	@Size(min=3, max=256)
	private String headline;

	@Column(name="description")
	@Size(min=64, max=1000)
	private String description;

	@Column(name="company_size")
	private String companySize;

	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy="company", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Funding> funding;

	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="company_industry",
			joinColumns=@JoinColumn(name="company_id"),
			inverseJoinColumns=@JoinColumn(name="industry_id"))
	private List<Industry> industries;

	@JsonIgnore
	@OneToMany(mappedBy="company", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Team> teams;

	@JsonIgnore
	@OneToMany(mappedBy="company", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Investment> investments;

	@JsonIgnore
	@OneToOne(mappedBy="company", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private CompanyMetrics companyMetrics;
	
	public Company() {}

	public Company(@NotNull @Size(min = 3, max = 32) String name, @NotNull @Size(min = 3, max = 32) String headQuarter, @NotNull Date foundedYear, @NotNull @Size(min = 3, max = 32) String type, @NotNull @Size(min = 3, max = 256) String headline, @Size(min = 64, max = 1000) String description, String companySize) {
		this.name = name;
		this.headQuarter = headQuarter;
		this.foundedYear = foundedYear;
		this.type = type;
		this.headline = headline;
		this.description = description;
		this.companySize = companySize;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Funding> getFunding() {
		return funding;
	}

	public void setFunding(List<Funding> funding) {
		this.funding = funding;
	}

	public List<Industry> getIndustries() {
		return industries;
	}

	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

	public CompanyMetrics getCompanyMetrics() {
		return companyMetrics;
	}

	public void setCompanyMetrics(CompanyMetrics companyMetrics) {
		this.companyMetrics = companyMetrics;
	}

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", name='" + name + '\'' +
				", headQuarter='" + headQuarter + '\'' +
				", foundedYear=" + foundedYear +
				", type='" + type + '\'' +
				", headline='" + headline + '\'' +
				", description='" + description + '\'' +
				", companySize=" + companySize +
				'}';
	}
}
