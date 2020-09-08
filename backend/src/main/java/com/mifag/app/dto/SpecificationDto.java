package com.mifag.app.dto;

import javax.validation.constraints.NotNull;

import com.mifag.app.entity.Specification;
import com.mifag.app.enums.TypeOfKeyEnum;

/**
 * Объект для отправки на клиент.
 */
public class SpecificationDto {
    private Long id;

    @NotNull
    private Integer weight;

    @NotNull
    private Integer length;

    @NotNull
    private Integer width;

    @NotNull
    private Boolean velocity;

    @NotNull
    private TypeOfKeyEnum typeOfKey;

    /**
     * Empty constructor.
     */
    public SpecificationDto() {

    }

    /**
     * Constructor.
     *
     * @param specification - entity.
     */
    public SpecificationDto(Specification specification) {
        this.id = specification.getId();
        this.weight = specification.getWeight();
        this.length = specification.getLength();
        this.width = specification.getWidth();
        this.velocity = specification.getVelocity();
        this.typeOfKey = specification.getTypeOfKey();
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
