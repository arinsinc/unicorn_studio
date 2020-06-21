package com.unicorn.studio.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="club")
public class Club {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="title")
	@NotNull
	@Size(min=3, max=32)
	private String title;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="company_club",
			joinColumns=@JoinColumn(name="club_id"),
			inverseJoinColumns=@JoinColumn(name="company_id"))
	private List<Company> companies;
	
	public Club() {}

	public Club(String title, List<Company> companies) {
		this.title = title;
		this.companies = companies;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Company> getCompany() {
		return companies;
	}

	public void setCompany(List<Company> companies) {
		this.companies = companies;
	}

	@Override
	public String toString() {
		return "Club [id=" + id + ", title=" + title + ", companies=" + companies + "]";
	}
	
	
	
}
