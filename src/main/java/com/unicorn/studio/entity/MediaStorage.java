package com.unicorn.studio.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicorn.studio.utils.IdGenerator;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="media_storage")
public class MediaStorage {
    @Id
    @GenericGenerator(name="media_storage_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "media_storage_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="file_name")
    @NotNull
    private String fileName;

    @Column(name="media_type")
    @NotNull
    private String mediaType;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToOne(mappedBy = "media", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    @JoinTable(
            name="companies_media_storages",
            joinColumns = @JoinColumn( name="media_id"),
            inverseJoinColumns = @JoinColumn( name="company_id")
    )
    private Company company;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    @JoinTable(
            name="investors_media_storages",
            joinColumns = @JoinColumn( name="media_id"),
            inverseJoinColumns = @JoinColumn( name="investor_id")
    )
    private Investor investor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    @JoinTable(
            name="startup_programs_media_storages",
            joinColumns = @JoinColumn( name="media_id"),
            inverseJoinColumns = @JoinColumn( name="program_id")
    )
    private StartupProgram startupProgram;

    public MediaStorage() {}

    public MediaStorage(@NotNull String fileName, @NotNull String mediaType) {
        this.fileName = fileName;
        this.mediaType = mediaType;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getObjectType() {
        return mediaType;
    }

    public void setObjectType(String mediaType) {
        this.mediaType = mediaType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public StartupProgram getStartupProgram() {
        return startupProgram;
    }

    public void setStartupProgram(StartupProgram startupProgram) {
        this.startupProgram = startupProgram;
    }

    @Override
    public String toString() {
        return "MediaStorage{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}
