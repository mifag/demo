package com.mifag.app.demo.dto;

public class CarDataDto {
    private Long id;
    private String tyC;
    private String moC;
    private Integer spC;
    private Integer poC;

    public String getTyC() {
        return tyC;
    }

    public void setTyC(String tyC) {
        this.tyC = tyC;
    }

    public String getMoC() {
        return moC;
    }

    public void setMoC(String moC) {
        this.moC = moC;
    }

    public Integer getSpC() {
        return spC;
    }

    public void setSpC(Integer spC) {
        this.spC = spC;
    }

    public Integer getPoC() {
        return poC;
    }

    public void setPoC(Integer poC) {
        this.poC = poC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
