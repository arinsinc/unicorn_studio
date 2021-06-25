package com.unicorn.studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.studio.utils.IdGenerator;
import com.stripe.model.SetupIntent;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="setup_intents")
public class StripeSetupIntent extends SetupIntent {
    @Id
    @GenericGenerator(name="setup_intent_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "setup_intent_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="client_secret")
    @NotNull
    private String clientSecret;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="customer_id")
    private StripeCustomer customer;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="payment_method_id")
    private StripePaymentMethod paymentMethod;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StripeSetupIntent() {
    }

    public String getUid() {
        return uid;
    }
}
