package com.azya.partstore.models;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    protected Part() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean needed;

    private Integer count;

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

    public Boolean getNeeded() {
        return needed;
    }

    public void setNeeded(Boolean needed) {
        this.needed = needed;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
