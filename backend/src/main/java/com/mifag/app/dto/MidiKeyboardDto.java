package com.mifag.app.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.mifag.app.entity.MidiKeyboard;

/**
 * Midi keyboard dto.
 */
public class MidiKeyboardDto {

    private Long id;

    @NotNull
    private String manufacturer;

    @NotNull
    private String model;

    @NotNull
    private Integer keysNumber;

    @NotNull
    private Boolean hasMidiOut;

    @NotNull
    private Long price;

    private Long specificationId;

    private List<OwnerDto> owners;

    /**
     * Empty constructor.
     */
    public MidiKeyboardDto() {

    }

    /**
     * Constructor.
     *
     * @param midiKeyboard - entity.
     */
    public MidiKeyboardDto(MidiKeyboard midiKeyboard) {
        this.id = midiKeyboard.getId();
        this.manufacturer = midiKeyboard.getManufacturer();
        this.model = midiKeyboard.getModel();
        this.keysNumber = midiKeyboard.getKeysNumber();
        this.hasMidiOut = midiKeyboard.getHasMidiOut();
        this.price = midiKeyboard.getPrice();
        this.specificationId = midiKeyboard.getSpecificationId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getKeysNumber() {
        return keysNumber;
    }

    public void setKeysNumber(Integer keysNumber) {
        this.keysNumber = keysNumber;
    }

    public Boolean getHasMidiOut() {
        return hasMidiOut;
    }

    public void setHasMidiOut(Boolean hasMidiOut) {
        this.hasMidiOut = hasMidiOut;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public List<OwnerDto> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerDto> owners) {
        this.owners = owners;
    }
}
