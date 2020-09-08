package com.mifag.app.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mifag.app.entity.Owner;
import com.mifag.app.enums.SexEnum;
import com.mifag.app.enums.SkillLevelEnum;

/**
 * Owner dto.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private SexEnum sex;

    @NotNull
    private Integer age;

    @NotNull
    private SkillLevelEnum skillLevel;

    private List<MidiKeyboardDto> midiKeyboardList;

    /**
     * Empty constructor.
     */
    public OwnerDto() {

    }

    /**
     * Constructor.
     *
     * @param owner - entity.
     */
    public OwnerDto(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.sex = owner.getSex();
        this.age = owner.getAge();
        this.skillLevel = owner.getSkillLevel();
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

    public List<MidiKeyboardDto> getMidiKeyboardList() {
        return midiKeyboardList;
    }

    public void setMidiKeyboardList(List<MidiKeyboardDto> midiKeyboardList) {
        this.midiKeyboardList = midiKeyboardList;
    }
}
