package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.CarDataDto;
import com.mifag.app.demo.dto.TableThreeDto;
import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public TableThreeDto getOneCar(Long carId) {
        Optional<Cars> cold = repcars.findById(carId);
        Cars mas = null;
        TableThreeDto tabData = new TableThreeDto();
        if (cold.isPresent()) {
            mas = cold.get();
            tabData = new TableThreeDto(mas.getId(), mas.getType(), mas.getModel(), mas.getSpeed(), mas.getPower());
        }
        return tabData;
    }

    public CarDataDto addCar(CarDataDto dataDto) {
        Cars bodyCars = new Cars();
        bodyCars.setType(dataDto.getTyC());
        bodyCars.setModel(dataDto.getMoC());
        bodyCars.setSpeed(dataDto.getSpC());
        bodyCars.setPower(dataDto.getPoC());
        Cars createdCar = repcars.save(bodyCars);
        Long nam = createdCar.getId();
        dataDto.setId(nam);
        return dataDto;
    }
}