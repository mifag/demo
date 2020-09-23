package com.mifag.app.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mifag.app.dto.MidiKeyboardDto;

/**
 * Midi Keyboard entity.
 */
@Entity
@Table(name = "midi_keyboard")
public class MidiKeyboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "keys_number")
    private Integer keysNumber;

    @Column(name = "has_midi_out")
    private Boolean hasMidiOut;

    @Column(name = "price")
    private Long price;

    @Column(name = "specification_id")
    private Long specificationId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "midiKeyboard")
    private List<OwnerMidiKeyboardMap> ownerMidiKeyboardMaps;

    /**
     * Empty constructor.
     */
    public MidiKeyboard() {

    }

    /**
     * Constructor.
     *
     * @param midiKeyboardDto .
     */
    public MidiKeyboard(MidiKeyboardDto midiKeyboardDto) {
        this.id = midiKeyboardDto.getId();
        this.manufacturer = midiKeyboardDto.getManufacturer();
        this.model = midiKeyboardDto.getModel();
        this.keysNumber = midiKeyboardDto.getKeysNumber();
        this.hasMidiOut = midiKeyboardDto.getHasMidiOut();
        this.price = midiKeyboardDto.getPrice();
        this.specificationId = midiKeyboardDto.getSpecificationId();
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

    public List<OwnerMidiKeyboardMap> getOwnerMidiKeyboardMaps() {
        return ownerMidiKeyboardMaps;
    }

    public void setOwnerMidiKeyboardMaps(List<OwnerMidiKeyboardMap> ownerMidiKeyboardMaps) {
        this.ownerMidiKeyboardMaps = ownerMidiKeyboardMaps;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }
}
