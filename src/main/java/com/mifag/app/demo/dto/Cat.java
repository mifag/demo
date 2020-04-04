package com.mifag.app.demo.dto;

public class Cat {
    private String name;
    private Integer paws;
    private boolean hasTail;

    public Cat() {

    }

    public Cat(String b) {
        this.name = b;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPaws() {
        return paws;
    }

    public void setPaws(Integer paws) {
        this.paws = paws;
    }

    public boolean isHasTail() {
        return hasTail;
    }

    public void setHasTail(boolean hasTail) {
        this.hasTail = hasTail;
    }
}
