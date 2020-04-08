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

    /**
     * К нам в контроллер приходит get-запрос по протоколу http.
     * Запрос попадает в метод getCars. (Этот метод показывает нам все записи в таблице cars)
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsRecords")
    public ResponseEntity<List<Cars>> getCars() {
        // Мы переходим из слоя Controller, отвечающего за общение по протоколу http, в слой Service, содержащий
        // в себе бизнес-логику данного API. Мы вызываем метод getAllCars сервиса СarService, представленного
        // переменной carSer.
        List<Cars> returnValue = carSer.getAllCars();
        // Полученный из метода getAllCars сервиса CarService, представленного объектом carSer, объект returnValue
        // класса List<Cars> отправлен обратно на клиент по протоколу http в ответ на пришедший запрос.
        return ResponseEntity.ok(returnValue);
    }

    /**
     * К нам в контроллер приходит get-запрос по протоколу http. Запрос содержит в себе параметр Id.
     * Запрос попадает в метод getCarsById. (Этот метод показывает нам запись с выбранным Id)
     * Парамет Id запроса попадает в переменную tableId типа Long.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsId")
    public ResponseEntity<TableThreeDto> getCarsById(@RequestParam(value = "id") Long tableId) {
        // Мы переходим из слоя Controller, отвечающего за общение по протоколу http, в слой Service, содержащий в себе
        // бизнес-логику данного API. Мы вызываем метод getOneCar сервиса СarService, представленного переменной carSer.
        // В метод getOneCar мы передаем переменную tableId типа Long
        TableThreeDto car = carSer.getOneCar(tableId);
        // Полученный из метода getOneCar сервиса CarService, представленного объектом carSer, объект класса
        // TableThreeDto присвоен объекту car класса TableThreeDto. Объект car  (который содержит в себе данные
        // о нужной записи), обернутый в класс ResponseEntity, отправлен обратно на клиент по протоколу http в
        // ответ на пришедший запрос.
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
        // записи в таблице cars), "обернутый" в класс ResponseEntity
    }

    /**
     * К нам в контроллер приходит put-запрос по протоколу http. Запрос содержит в себе тело и переменную пути.
     * Запрос попадает в метод update. (Этот метод редактирует существующую запись в таблице cars.)
     * тело http запроса попадает в объект spq класса CarDataDto. Переменная пути carId попадает в переменную carUpdate
     * типа Long.
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<Cars> update(@RequestBody CarDataDto spq,
                                       @PathVariable(value = "carId") Long carUpdate) {
        // Мы переходим из слоя Controller, отвечающего за общение по протоколу http, в слой Service, содержащий в
        // себе бизнес-логику данного API. Мы вызываем метод replaceCar сервиса carService (carSer). В метод replaceCar
        // мы передаем объект spq класса CarDataDto и переменную carUpdate типа Long.
        Cars pkk = carSer.replaceCar(spq, carUpdate);
        // Полученный из метода replaceCar сервиса CarService, представленного объектом carSer, объект класса Cars
        // присвоен объекту pkk класса Cars. Объект pkk ("обернутый" в класс ResponseEntity)
        // отправлен обратно на клиент по протоколу HTTP в ответ на пришедший запрос.
        return ResponseEntity.ok(pkk);
        // метод update возвращает объект класса Cars (который содержит в себе данные об измененной записи в таблице
        // cars), "обернутый" в класс ResponseEntity
    }

    /**
     * К нам в контроллер приходит delete-запрос по протоколу http. Запрос содержит в себе переменную пути.
     * Запрос попадает в метод delCar. (Этот метод удаляет запись в таблице cars.)
     * Переменная пути carId попадает в переменную carIdDelete типа Long.
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/body/{carId}")
    public ResponseEntity<HttpStatus> delCar(@PathVariable(value = "carId") Long carIdDelete) {
        carSer.deleteCar(carIdDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        // полученный из метода delCar http-статус "No_content" отправлен обратно на клиент по протоколу http в ответ
        // на пришедший запрос.
    }
}