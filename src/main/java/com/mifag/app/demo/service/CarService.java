package com.mifag.app.demo.service;

import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarService {

    private final CarsRepository repcars;

    @Autowired
    public CarService(CarsRepository repcars) {
        this.repcars = repcars;
    }

    public List<Cars> getAllCars() {
        Iterable<Cars> records = repcars.findAll();
        List<Cars> retValue = new ArrayList<>();
        for (Cars record : records) {
            retValue.add(record);
        }
        return retValue;
    }
}