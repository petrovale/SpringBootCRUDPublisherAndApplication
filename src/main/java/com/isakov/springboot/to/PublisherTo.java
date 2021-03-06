package com.isakov.springboot.to;

import org.hibernate.validator.constraints.*;

import java.io.Serializable;

public class PublisherTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    @SafeHtml
    private String name;

    @Length(min = 5, max = 32)
    private String password;

    public PublisherTo() {
    }

    public PublisherTo(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PublisherTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
