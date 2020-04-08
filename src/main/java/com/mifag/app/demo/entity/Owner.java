package com.mifag.app.demo.entity;

import com.mifag.app.demo.dto.OwnerDto;
import com.mifag.app.demo.enums.SexEnum;
import com.mifag.app.demo.enums.SkillLevelEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private SexEnum sex;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevelEnum skillLevel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<OwnerMidiKeyboardMap> ownerMidiKeyboardMaps;

    public Owner() {

    }

    public Owner(OwnerDto ownerDto) {
        this.id = ownerDto.getId();
        this.name = ownerDto.getName();
        this.sex = ownerDto.getSex();
        this.age = ownerDto.getAge();
        this.skillLevel = ownerDto.getSkillLevel();
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

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SkillLevelEnum getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevelEnum skillLevel) {
        this.skillLevel = skillLevel;
    }

    public List<OwnerMidiKeyboardMap> getOwnerMidiKeyboardMaps() {
        return ownerMidiKeyboardMaps;
    }

    public void setOwnerMidiKeyboardMaps(List<OwnerMidiKeyboardMap> ownerMidiKeyboardMaps) {
        this.ownerMidiKeyboardMaps = ownerMidiKeyboardMaps;
    }
}