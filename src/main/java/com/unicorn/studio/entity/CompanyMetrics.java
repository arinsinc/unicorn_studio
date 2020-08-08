package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="company_metrics")
public class CompanyMetrics {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="revenue")
    @NotNull
    private int revenue;

    @Column(name="market_cap")
    @NotNull
    private int marketCap;

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

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(int marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public String toString() {
        return "CompanyMetrics{" +
                "id=" + id +
                ", revenue='" + revenue + '\'' +
                ", marketCap='" + marketCap + '\'' +
                '}';
    }
}
