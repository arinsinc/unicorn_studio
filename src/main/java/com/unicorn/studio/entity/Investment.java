package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="investment")
public class Investment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="amount")
    @NotNull
    private int amount;

    @Column(name="series_type")
    @NotNull
    @Size(min=3, max=32)
    private String seriesType;

    @JsonIgnore
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="company_id")
    private Company company;

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

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
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

    @Override
    public String toString() {
        return "Investment{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", seriesType='" + seriesType + '\'' +
                '}';
    }
}
