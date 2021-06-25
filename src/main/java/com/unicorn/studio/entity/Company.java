package com.unicorn.studio.entity;



import com.unicorn.studio.utils.IdGenerator;
import com.unicorn.studio.validation.AllowedValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="companies")
public class Company {
    @Id
    @GenericGenerator(name="company_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "company_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="name")
    @NotNull
    @Size(min=2, max=132)
    private String name;

    @Column(name="profile")
    @NotNull
    private String profile;

    @Column(name="url")
    @Size(max=132)
    private String url;

    @Column(name="founded_year")
    @NotNull
    @Size(min=4, max=4)
    private String foundedYear;

    @Column(name="org_type")
    @NotNull
    @Size(min=2, max=16)
    @AllowedValue(type = "Org")
    private String orgType;

    @Column(name="size")
    @NotNull
    @Size(min=2, max=16)
    private String size;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="industry_id")
    private Industry industry;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="location_id")
    private City headquarter;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name="companies_media_storages",
            joinColumns = @JoinColumn( name="company_id"),
            inverseJoinColumns = @JoinColumn( name="media_id")
    )
    private List<MediaStorage> media;

    @JsonIgnore
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<CompanyMetrics> companyMetrics;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    @JoinTable(name = "companies_investors",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "investor_id", referencedColumnName = "id"))
    private List<Investor> investors;

    @JsonIgnore
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<Funding> fundingList;

    public Company() {}

    public Company(@NotNull @Size(min = 2, max = 132) String name, @NotNull String profile, @Size(max = 132) String url, @NotNull @Size(min = 2, max = 32) String foundedYear, @NotNull @Size(min = 2, max = 16) String orgType, @NotNull @Size(min = 2, max = 16) String size, City headquarter, Industry industry) {
        this.name = name;
        this.profile = profile;
        this.url = url;
        this.foundedYear = foundedYear;
        this.orgType = orgType;
        this.size = size;
        this.industry = industry;
        this.headquarter = headquarter;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(String foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public City getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(City headquarter) {
        this.headquarter = headquarter;
    }

    public List<CompanyMetrics> getCompanyMetrics() {
        return companyMetrics;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public List<Funding> getFundingList() {
        return fundingList;
    }

    public User getUser() {
        return user;
    }

    public List<MediaStorage> getMedia() {
        return media;
    }

    public void setMedia(List<MediaStorage> media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", uid=" + uid + '\'' +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                ", url='" + url + '\'' +
                ", foundedYear='" + foundedYear + '\'' +
                ", orgType='" + orgType + '\'' +
                ", size='" + size + '\'' +
                ", industry=" + industry +
                ", headquarter=" + headquarter +
                '}';
    }
}
