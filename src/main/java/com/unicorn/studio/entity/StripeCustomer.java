package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.studio.utils.IdGenerator;
import com.stripe.model.Address;
import com.stripe.model.Customer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="customers")
public class StripeCustomer extends Customer {
    @Id
    @GenericGenerator(name="customer_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "customer_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="name")
    @NotNull
    @Size(min=2, max=64)
    private String name;

    @Column(name="email")
    @NotNull
    @Size(min=2, max=64)
    private String email;

    @Column(name="address_line1")
    @Size(max=132)
    private String addressLine1;

    @Column(name="address_line2")
    @Size(max=132)
    private String addressLine2;

    @Column(name="city")
    @Size(max=132)
    private String city;

    @Column(name="state")
    @Size(max=132)
    private String state;

    @Column(name="country")
    @Size(max=64)
    private String country;

    @Column(name="postal_code")
    @Size(max=16)
    private String postalCode;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<StripePaymentIntent> paymentIntents;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
    private List<StripePaymentMethod> paymentMethods;

    public StripeCustomer() {
    }

    public String getUid() {
        return uid;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public Address getAddress() {
        Address address  = new Address();
        address.setLine1(this.getAddressLine1());
        address.setLine2(this.getAddressLine2());
        address.setCity(this.getCity());
        address.setState(this.getState());
        address.setCountry(this.getCountry());
        address.setPostalCode(this.getPostalCode());
        return address;
    }
}
