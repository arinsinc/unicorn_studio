package com.unicorn.studio.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.studio.utils.IdGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="funding")
public class Funding {
    @Id
    @GenericGenerator(name="funding_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "funding_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="amount")
    @NotNull
    private long amount;

    @Column(name="currency")
    @NotNull
    private char currency = '$';

    @Column(name="funding_type")
    @NotNull
    @Size(min=2, max=64)
    private String fundingType;

    @Column(name="date")
    @NotNull
    private LocalDateTime fundingDate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="company_id")
    private Company company;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="investor_id")
    private Investor investor;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Funding() {
    }

    public Funding(int amount, char currency, String fundingType, LocalDateTime date, Investor investor, Company company) {
        this.amount = amount;
        this.currency = currency;
        this.fundingType = fundingType;
        this.fundingDate = date;
        this.investor = investor;
        this.company = company;
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

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getFundingType() {
        return fundingType;
    }

    public void setFundingType(String fundingType) {
        this.fundingType = fundingType;
    }

    public LocalDateTime getDate() {
        return fundingDate;
    }

    public void setDate(LocalDateTime date) {
        this.fundingDate = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Funding{" +
                "id=" + id +
                ", investor='" + investor + '\'' +
                ", amount='" + amount + '\'' +
                ", fundingType='" + fundingType + '\'' +
                ", date=" + fundingDate +
                '}';
    }
}
