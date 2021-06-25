package com.unicorn.studio.entity;



import com.unicorn.studio.utils.IdGenerator;
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
@Table(name="roles")
public class Role {
    @Id
    @GenericGenerator(name="role_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "role_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="name")
    @NotNull
    @Size(min=2, max=16)
    @AllowedValue(type = "Role")
    private String name;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy="role", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<User> user;

    public Role() {}

    public Role(@NotNull @Size(min = 2, max = 16) String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
