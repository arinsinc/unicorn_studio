package com.unicorn.studio.entity;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="industries")
public class Industry {
    @Id
    @GenericGenerator(name="industry_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "industry_seq")
    @Column(name="id")
    private long id;

    @Column(name="name")
    @NotNull
    @Size(min=2, max=64)
    private String name;

    @Column(name="category")
    @Size(min=2, max=64)
    private String group;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Industry() {}

    public Industry(@NotNull @Size(min = 2, max = 64) String name, @Size(min = 2, max = 64) String group) {
        this.name = name;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Industry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
