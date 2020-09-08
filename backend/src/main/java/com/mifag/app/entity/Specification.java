package com.mifag.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mifag.app.dto.SpecificationDto;
import com.mifag.app.enums.TypeOfKeyEnum;

/**
 * Entity спецификации миди-клавиатуры.
 */
@Entity
@Table(name = "specification")
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    @Column(name = "velocity")
    private Boolean velocity;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_key")
    private TypeOfKeyEnum typeOfKey;

    /**
     * Empty constructor.
     */
    public Specification() {

    }

    /**
     * Constructor.
     *
     * @param specificationDto .
     */
    public Specification(SpecificationDto specificationDto) {
        this.id = specificationDto.getId();
        this.weight = specificationDto.getWeight();
        this.length = specificationDto.getLength();
        this.width = specificationDto.getWidth();
        this.velocity = specificationDto.getVelocity();
        this.typeOfKey = specificationDto.getTypeOfKey();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getVelocity() {
        return velocity;
    }

    public void setVelocity(Boolean velocity) {
        this.velocity = velocity;
    }

    public TypeOfKeyEnum getTypeOfKey() {
        return typeOfKey;
    }

    public void setTypeOfKey(TypeOfKeyEnum typeOfKey) {
        this.typeOfKey = typeOfKey;
    }
}
