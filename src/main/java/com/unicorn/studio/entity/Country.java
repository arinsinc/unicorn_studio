package com.unicorn.studio.entity;



import com.unicorn.studio.validation.AllowedValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="countries")
public class Country {
    @Id
    @GenericGenerator(name="country_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "country_seq")
    @Column(name="id")
    private long id;

    @Column(name="name")
    @NotNull
    @Size(min=2, max=64)
    private String name;

    @Column(name="country_code")
    @NotNull
    @Size(min=2, max=64)
    private String countryCode;

    @Column(name="region")
    @Size(min=2, max=64)
    @AllowedValue(type = "Region")
    private String region;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy="country", fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    private List<State> states;

    public Country() {}

    public Country(@NotNull @Size(min = 2, max = 64) String name, @NotNull @Size(min = 2, max = 64) String countryCode, @Size(min = 2, max = 64) String region) {
        this.name = name;
        this.countryCode = countryCode;
        this.region = region;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<State> getStates() {
        return states;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", region='" + region + '\'' +
                ", states=" + states +
                '}';
    }
}
