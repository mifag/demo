package com.mifag.app.demo.oldClasses;

import org.springframework.stereotype.Component;

@Component
public class ServiceForFirstTable {

    public Integer plusTwo(Integer plus) {
        plus = plus + 2;
        return plus;
    }
}