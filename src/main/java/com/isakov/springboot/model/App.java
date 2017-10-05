package com.isakov.springboot.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="APPS")
public class App implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="NAME", nullable=false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publisher publisher;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "app")
    private List<AppVersion> versions;

    public App() {
    }

    public App(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }

    public List<AppVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<AppVersion> versions) {
        this.versions = versions;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (id != null ? !id.equals(app.id) : app.id != null) return false;
        if (name != null ? !name.equals(app.name) : app.name != null) return false;
        return publisher != null ? publisher.equals(app.publisher) : app.publisher == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }
}
