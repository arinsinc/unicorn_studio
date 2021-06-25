package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.studio.utils.IdGenerator;
import com.stripe.model.PaymentMethod;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="payment_methods")
public class StripePaymentMethod extends PaymentMethod {
    @Id
    @GenericGenerator(name="payment_method_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "payment_method_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="type")
    @NotNull
    @Size(min=2, max=64)
    private String type;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="customer_id")
    private StripeCustomer customer;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StripePaymentMethod() {
    }

    public String getUid() {
        return uid;
    }
}
