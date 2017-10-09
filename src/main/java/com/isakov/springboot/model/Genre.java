package com.isakov.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="GENRES")
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer genreid;

    @NotBlank
    @Column(name="NAME", nullable=false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private Set<App> apps;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Integer getGenreid() {
        return genreid;
    }

    public void setGenreid(Integer genreid) {
        this.genreid = genreid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreid=" + genreid +
                ", name='" + name + '\'' +
                '}';
    }
}
