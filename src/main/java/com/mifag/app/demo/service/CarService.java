package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.CarDataDto;
import com.mifag.app.demo.dto.TableThreeDto;
import com.mifag.app.demo.entity.Cars;
import com.mifag.app.demo.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    /**
     * Метод addCar содержит бизнес-логику API addCarDataDto, находящегося в контроллере CarsController.
     * Этот метод добавляет запись в таблицу cars и возвращает объект класса CarDataDto, содержащий в себе
     * информацию о добавленной в таблицу cars записи.
     * dataDto - Данный объект класса CarDataDto содержит в себе данные объекта cdd, переданного из метода
     * addCarDataDto класса (Контроллера) CarsController. Этот объект используется для заполнения Entity Cars данными.
     */
    public CarDataDto addCar(CarDataDto dataDto) {
        Cars bodyCars = new Cars();
        bodyCars.setType(dataDto.getTyC());
        bodyCars.setModel(dataDto.getMoC());
        bodyCars.setSpeed(dataDto.getSpC());
        bodyCars.setPower(dataDto.getPoC());
        // Здесь происходит вызов репозитория CarsRepository, представленного объектом repcars. Вызывается
        // метод save репозитория CarsRepository, представленного объетом repcars. В метод save передается
        // entity Cars представленная объектом bodyCars. Возвращаемое методом save значение содержит в себе
        // информацию о добавленной (сохранённой) записи в таблицу cars и является объектом класса Cars (Entity).
        // Возвращаемый методом save репозитория CarsRepository, представленного объектом repcars, объект приравнен
        // к новому объекту createdCar класса Cars.
        Cars createdCar = repcars.save(bodyCars);
        Long nam = createdCar.getId();
        CarDataDto carto = new CarDataDto();
        carto.setId(nam);
        carto.setMoC(createdCar.getModel());
        carto.setPoC(createdCar.getPower());
        carto.setSpC(createdCar.getSpeed());
        carto.setTyC(createdCar.getType());
        // Возвращаемое значение метода addCar (являющееся объектом класса CarDataDto)
        // передается обратно в метод addCarDataDto контроллёра CarsController в объект dff.
        return carto;
        //Метод addCar возвращает данные о добавленной записи в таблицу cars (объект carto).
    }

}