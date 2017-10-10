package com.isakov.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="APPS")
public class App extends BaseEntity {

    @NotBlank
    @Column(name="NAME", nullable=false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Publisher publisher;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "app_genre", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "genreid") })
    private Set<Genre> genres = new HashSet<>(0);

    public App() {
    }

    public App(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }

    public App(String name, Publisher publisher, Genre genre) {
        this.name = name;
        this.publisher = publisher;
        this.getGenres().add(genre);
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public boolean isNew() {
        return (getId() == null);
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
