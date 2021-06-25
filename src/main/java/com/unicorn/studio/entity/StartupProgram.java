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
@Table(name="startup_programs")
public class StartupProgram {
    @Id
    @GenericGenerator(name="startup_program_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "startup_program_seq")
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

    @Column(name="program_type")
    @NotNull
    @Size(min=2, max=16)
    private String programType;

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
    @OneToMany(fetch = FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name="startup_programs_media_storages",
            joinColumns = @JoinColumn( name="program_id"),
            inverseJoinColumns = @JoinColumn( name="media_id")
    )
    private List<MediaStorage> media;

    public StartupProgram() {
    }

    public StartupProgram(String name, String profile, String url, String foundedYear, String programType, Industry industry, City headquarter) {
        this.name = name;
        this.profile = profile;
        this.url = url;
        this.foundedYear = foundedYear;
        this.programType = programType;
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

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MediaStorage> getMedia() {
        return media;
    }

    public void setMedia(List<MediaStorage> media) {
        this.media = media;
    }
}
