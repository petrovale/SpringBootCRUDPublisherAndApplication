package com.isakov.springboot.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="APP_VERSIONS")
public class AppVersion implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="NAME", nullable=false)
    private String name;

    @Column(name="ACTIVE", nullable=false, columnDefinition = "bool default false")
    private boolean active = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "APP_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private App app;

    public AppVersion() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppVersion that = (AppVersion) o;

        if (active != that.active) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return app != null ? app.equals(that.app) : that.app == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (app != null ? app.hashCode() : 0);
        return result;
    }
}
