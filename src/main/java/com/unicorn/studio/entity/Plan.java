package com.unicorn.studio.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="plans")
public class Plan {
    @Id
    @GenericGenerator(name="plan_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "plan_seq")
    @Column(name="id")
    private long id;

    @Column(name="name")
    @NotNull
    @Size(min=2, max=16)
    private String planName;

    @Column(name="plan_id")
    @NotNull
    @Size(max=64)
    private String planId = IdGenerator.generateUId();

    @Column(name="price")
    @NotNull
    @Size(min=2, max=4)
    private String price;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy="plan", fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    private List<Subscription> subscriptions;

    public Plan() {}

    public Plan(@NotNull @Size(min = 2, max = 16) String planName, @NotNull @Size(min = 2, max = 16) String planId, @NotNull @Size(min = 2, max = 4) String price) {
        this.planName = planName;
        this.planId = planId;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", planName='" + planName + '\'' +
                ", planId='" + planId + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
