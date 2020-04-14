package com.mifag.app.demo.oldClasses;

public class TableThreeDto {
    private Long idCars;
    private String typeCar;
    private String modelCar;
    private Integer speedCar;
    private Integer powerCar;

    public TableThreeDto() {

    }

    public TableThreeDto(Long a, String b, String c, Integer d, Integer e) {
        this.idCars = a;
        this.typeCar = b;
        this.modelCar = c;
        this.speedCar = d;
        this.powerCar = e;
    }

    public Long getIdCars() {
        return idCars;
    }

    public void setIdCars(Long idCars) {
        this.idCars = idCars;
    }

    public String getTypeCar() {
        return typeCar;
    }

    public void setTypeCar(String typeCar) {
        this.typeCar = typeCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public Integer getSpeedCar() {
        return speedCar;
    }

    public void setSpeedCar(Integer speedCar) {
        this.speedCar = speedCar;
    }

    public Integer getPowerCar() {
        return powerCar;
    }

    public void setPowerCar(Integer powerCar) {
        this.powerCar = powerCar;
    }
}
