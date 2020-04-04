package com.mifag.app.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "table_two")
public class TableTwo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationality;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String n) {
        this.nationality = n;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
