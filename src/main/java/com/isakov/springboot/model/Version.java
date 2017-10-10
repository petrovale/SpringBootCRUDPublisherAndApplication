package com.isakov.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="APP_VERSIONS", uniqueConstraints = {@UniqueConstraint(columnNames = {"APP_ID", "NAME"}, name = "versions_unique_app_version_idx")})
public class Version extends BaseEntity  {

    @NotBlank
    @Column(name="NAME", nullable=false)
    private String name;

    @Column(name="ACTIVE", nullable=false, columnDefinition = "bool default false")
    private boolean active = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APP_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private App app;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private Publisher publisher;

    public Version() {
    }

    public Version(String name, boolean active, App app, Publisher publisher) {
        this.name = name;
        this.active = active;
        this.app = app;
        this.publisher = publisher;
    }

    public boolean isNew() {
        return (getId() == null);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "Version{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
