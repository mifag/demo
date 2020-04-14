package com.mifag.app.demo.oldClasses;

import javax.persistence.*;

@Entity
@Table(name = "table_one")
public class TableOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

  // public MidiKeyboardDto(Long a, String b, String c, Integer d, Boolean e, Long f) {
//     this.id = a;
//     this.manufacturer = b;
//     this.model = c;
//     this.keysNumber = d;
//     this.hasMidiOut = e;
//     this.price = f;
// }
