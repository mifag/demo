package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.CarDataDto;
import com.mifag.app.demo.dto.TableThreeDto;
import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/table/cars")
public class CarsController {
    private final CarsRepository repcars;

    @Autowired
    public CarsController(CarsRepository carstypes) {
        this.repcars = carstypes;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsRecords")
    public ResponseEntity<List<Cars>> getCars() {

        Iterable<Cars> records = repcars.findAll();
        List<Cars> returnValue = new ArrayList<>();
        for (Cars record : records) {
            returnValue.add(record);

        }
        return ResponseEntity.ok(returnValue);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsId")
    public ResponseEntity<TableThreeDto> getTableTwoById(@RequestParam(value = "id") Long tableId) {
        Optional<Cars> cold = repcars.findById(tableId);
        Cars mas = null;
        TableThreeDto tabData = new TableThreeDto();
        if (cold.isPresent()) {
            mas = cold.get();
            tabData.setIdCars(mas.getId());
            mas = new Cars();

        }
        return ResponseEntity.ok(tabData);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cars> addCars() {

        Cars table = new Cars();
        table.setType("sedan");
        table.setModel("audi");
        table.setSpeed(200);
        table.setPower(130);
        Cars createdCars = repcars.save(table);
        return ResponseEntity.ok(createdCars);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/body")
    public ResponseEntity<CarDataDto> addCarDataDto(@RequestBody CarDataDto cdd) {
        Cars bodyCars = new Cars();
        bodyCars.setType(cdd.getTyC());
        bodyCars.setModel(cdd.getMoC());
        bodyCars.setSpeed(cdd.getSpC());
        bodyCars.setPower(cdd.getPoC());
        bodyCars = repcars.save(bodyCars);
        Long nam = bodyCars.getId();
        cdd.setId(nam);
        return ResponseEntity.ok(cdd);

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<Cars> update (@RequestBody CarDataDto sgt,
                                              @PathVariable(value = "carId") Long carIdToUpdate) {
        Optional<Cars> updCar = repcars.findById(carIdToUpdate);
        if (updCar.isPresent()) {
            Cars detcars = updCar.get();
            detcars.setType(sgt.getTyC());
            detcars.setModel(sgt.getMoC());
            detcars.setSpeed(sgt.getSpC());
            detcars.setPower(sgt.getPoC());
            repcars.save(detcars);

            return ResponseEntity.ok(detcars);
        }
        Cars nonCars = null;
        return ResponseEntity.ok(nonCars);

    }

}