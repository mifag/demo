package com.mifag.app.demo.dto;

public class MidiKeyboardDto {

    private Long id;
    private String manufacturer;
    private String model;
    private Integer keysNumber;
    private Boolean hasMidiOut;
    private Long price;

    public MidiKeyboardDto() {

    }

    public MidiKeyboardDto(Long a, String b, String c, Integer d, Boolean e, Long f) {
        this.id = a;
        this.manufacturer = b;
        this.model = c;
        this.keysNumber = d;
        this.hasMidiOut = e;
        this.price = f;
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
}
