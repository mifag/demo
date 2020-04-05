package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.CarDataDto;
import com.mifag.app.demo.dto.TableThreeDto;
import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.repository.CarsRepository;
import com.mifag.app.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/table/cars")
public class CarsController {

    private final CarService carSer;
    private final CarsRepository repcars;

    @Autowired
    public CarsController(CarsRepository carstypes, CarService carServ) {
        this.repcars = carstypes;
        this.carSer = carServ;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsRecords")
    public ResponseEntity<List<Cars>> getCars() {
        List<Cars> returnValue = carSer.getAllCars();
        return ResponseEntity.ok(returnValue);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsId")
    public ResponseEntity<TableThreeDto> getCarsById(@RequestParam(value = "id") Long tableId) {
        TableThreeDto car = carSer.getOneCar(tableId);
        return ResponseEntity.ok(car);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/body")
    public ResponseEntity<CarDataDto> addCarDataDto(@RequestBody CarDataDto cdd) {
        CarDataDto dff = carSer.addCar(cdd);
        return ResponseEntity.ok(dff);
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

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<HttpStatus> delCar(@PathVariable(value = "carId") Long carIdDelete) {
        repcars.deleteById(carIdDelete);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);

    }
}