package com.isakov.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PUBLISHERS")
public class Publisher extends BaseEntity {

    @NotEmpty
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
    @JsonIgnore
    private List<App> apps;

    public Publisher() {
    }

    public Publisher(String name, String passwordHash, String role) {
        super();
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
