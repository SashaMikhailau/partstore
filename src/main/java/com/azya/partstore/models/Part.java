package com.azya.partstore.models;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part implements Comparable<Part> {
    public Part() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", count=" + count +
                '}';
    }

    private String name;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PartType type;

    private int count;

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

    public PartType getType() {
        return type;
    }

    public void setType(PartType type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isValid() {
        if (count <= 0) {
            return false;
        }
        if(type==null) return false;
        if (name.length() < 4) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Part part) {
        return id.compareTo(part.getId());
    }
}
