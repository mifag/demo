package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.CarDataDto;
import com.mifag.app.demo.dto.TableThreeDto;
import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table/cars")
public class CarsController {

    private final CarService carSer;

    @Autowired
    public CarsController(CarService carServ) {
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

    /**
     * К нам в контроллёр приходит post-запрос по протоколу http. Запрос содержит в себе тело. Запрос попадает в метод
     * addCarDataDto. (Этот метод создает новую запись в таблице cars.)
     * тело http запроса попадает в объект cdd класса CarDataDto.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/body")
    public ResponseEntity<CarDataDto> addCarDataDto(@RequestBody CarDataDto cdd) {
        // Мы переходим из слоя Controller, отвечающего за общение по протоколу http, в слой Service, содержащий в
        // себе бизнес-логику данного API. Мы вызываем метод addCar сервиса CarService, представленного переменной
        // carSer. В метод addCar мы передаем объект cdd класса CarDataDto.
        CarDataDto dff = carSer.addCar(cdd);
        // Полученный из метода addCar сервиса CarService, представленного объектом carSer, объект класса CarDataDto
        // присвоен объекту dff класса CarDataDto. Объект dff ("обернутый" в класс ResponseEntity)
        // отправлен обратно на клиент по протоколу HTTP в ответ на пришедший запрос.
        return ResponseEntity.ok(dff);
        // метод addCarDataDto возвращает объект класса CarDataDto (который содержит в себе данные о созданной
        //записи в таблице cars), "обернутый" в класс ResposeEntity
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<Cars> update(@RequestBody CarDataDto spq,
                                       @PathVariable(value = "carId") Long carUpdate) {
        Cars pkk = carSer.replaceCar(spq, carUpdate);
        return ResponseEntity.ok(pkk);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<HttpStatus> delCar(@PathVariable(value = "carId") Long carIdDelete) {
        carSer.deleteCar(carIdDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}