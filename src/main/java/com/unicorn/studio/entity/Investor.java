package com.unicorn.studio.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.studio.utils.IdGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="investors")
public class Investor {
    @Id
    @GenericGenerator(name="investor_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "investor_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="full_name")
    @NotNull
    @Size(min=2, max=64)
    private String fullName;

    @Column(name="email")
    @NotNull
    @Size(min=2, max=64)
    private String email;

    @Column(name="title")
    @NotNull
    @Size(max=64)
    private String title;

    @Column(name="profile")
    @NotNull
    private String profile;

    @Column(name="investor_since")
    @NotNull
    private LocalDateTime investorSince;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name="investors_media_storages",
            joinColumns = @JoinColumn( name="investor_id"),
            inverseJoinColumns = @JoinColumn( name="media_id")
    )
    private List<MediaStorage> media;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "companies_investors",
            joinColumns = @JoinColumn(name = "investor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"))
    private List<Company> companies;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "investors_industries",
            joinColumns = @JoinColumn(name = "investor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "industry_id", referencedColumnName = "id"))
    private List<Industry> industries;

    @JsonIgnore
    @OneToMany(mappedBy = "investor", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<Equity> equities;

    @JsonIgnore
    @OneToMany(mappedBy = "investor", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<Funding> fundingList;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Investor() {
    }

    public Investor(String fullName, String email, String title, String profile, LocalDateTime investorSince, List<Industry> industries) {
        this.fullName = fullName;
        this.email = email;
        this.title = title;
        this.profile = profile;
        this.investorSince = investorSince;
        this.industries = industries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public LocalDateTime getInvestorSince() {
        return investorSince;
    }

    public void setInvestorSince(LocalDateTime investorSince) {
        this.investorSince = investorSince;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Equity> getEquities() {
        return equities;
    }

    public List<Funding> getFundingList() {
        return fundingList;
    }

    public List<Industry> getIndustries() {
        return industries;
    }

    public List<MediaStorage> getMedia() {
        return media;
    }

    public void setMedia(List<MediaStorage> media) {
        this.media = media;
    }
}
