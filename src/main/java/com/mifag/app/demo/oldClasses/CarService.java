package com.mifag.app.demo.oldClasses;

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

    /**
     * Метод getAllCars содержит бизнес-логику API getCars, находящегося в контроллере CarsController.
     * Этот метод находит все записи таблицы сars и возвращает объект класса List<Cars>, содержащий информацию
     * о записях
     */
    public List<Cars> getAllCars() {
        // Здесь происходит вызов репозитория CarsRepository, представленного объектом repcars. Вызывается метод
        // findAll репозитория CarsRepository, представленного объетом repcars.
        Iterable<Cars> records = repcars.findAll();
        // Создается новый объект retValue класса List<Cars>
        List<Cars> retValue = new ArrayList<>();
        // В объект retValue добавляются все записи, которые есть в таблице cars
        for (Cars record : records) {
            retValue.add(record);
        }
        // Возвращаемое значение метода getAllCars (Являющееся объектом класса List<Cars>) передается обратно в метод
        // getCars контроллера CarsController в объект returnValue
        return retValue;
    }

    /**
     * Метод getOneCar содержит бизнес-логику API getCarsById, находящегося в контроллере CarsController.
     * Этот метод находит запись в таблице по Id и возвращает объект класса TableThreeDto, содержащий информацию
     * о записи.
     * carId - переменная типа long - содержит в себе данные переменной tableId, переданной из метода getCarsById
     * класса CarsController. Этот объект содержит в себе id записи, которую следует найти.
     */
    public TableThreeDto getOneCar(Long carId) {
        // Здесь происходит вызов репозитория CarsRepository, представленного объектом repcars. Вызывается метод
        // findById репозитория CarsRepository, представленного объетом repcars. В метод findById передается
        // переменная carId типа Long
        // Полученный объект приравнен к объекту cold класса Optional<Cars> (обертка,для защиты от случая, когда
        // запись не найдена.
        Optional<Cars> cold = repcars.findById(carId);
        // объявляется переменная mas класса Cars
        Cars mas = null;
        // Создается новый объект tabData класса TableThreeDto
        TableThreeDto tabData = new TableThreeDto();
        // если объект cold содержит данные, то выполняется условие if
        if (cold.isPresent()) {
            // В объект mas класса Cars передаются данные взятые методом get из объекта cold.
            mas = cold.get();
            // Объекту tabData класса TableThreeDto присваиваются значения, взятые из mas класса Cars.
            // Вызывается коструктор класса TableThreeDto, принимающий указанные параметры.
            tabData = new TableThreeDto(mas.getId(), mas.getType(), mas.getModel(), mas.getSpeed(), mas.getPower());
        }
        // Возвращаемое значение метода getOneCar (являющееся объектом класса TableThreeDto)передается обратно
        // в метод getCarsById контроллёра CarsController в объект car.
        return tabData;
    }

    /**
     * Метод addCar содержит бизнес-логику API addCarDataDto, находящегося в контроллере CarsController.
     * Этот метод добавляет запись в таблицу cars и возвращает объект класса CarDataDto, содержащий в себе
     * информацию о добавленной в таблицу cars записи.
     * dataDto - Данный объект класса CarDataDto содержит в себе данные объекта cdd, переданного из метода
     * addCarDataDto класса (Контроллера) CarsController. Этот объект используется для заполнения Entity
     * (репрезентация в коде строки таблицы сars) Cars данными.
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


    /**
     * Метод replaceCar содержит в себе бизнес-логику API update, находящегося в контроллере CarsController.
     * Этот метод изменяет существующую запись в таблице cars и возвращает объект класса Cars, содержащий в себе
     * информацию о измененной в таблицу cars записи и её Id.
     * sgt - объект класса CarDataDto - содержит в себе данные spq, переданного из метода update класса (контроллера)
     * CarsController. Этот объект используется для заполнения Entity Cars данными.
     * carId - переменная типа long - содержит в себе данные переменной carUpdate, переданной из метода update класса
     * CarsController. Этот объект содержит id записи, которую следует отредактировать.
     */
    public Cars replaceCar(CarDataDto sgt, Long carId) {
        Optional<Cars> updCar = repcars.findById(carId);
        // если значение updCar (полученное из репозитория по Id, равному переменной carId) присутствует - то мы
        // переходим в блок if
        if (updCar.isPresent()) {
            // Мы вынимаем Optional<Cars> объект класса Cars и присваиваем его значение объекту detcars
            Cars detcars = updCar.get();
            detcars.setType(sgt.getTyC());
            detcars.setModel(sgt.getMoC());
            detcars.setSpeed(sgt.getSpC());
            detcars.setPower(sgt.getPoC());
            // Здесь происходит вызов репозитория CarsRepository, представленного объектом repcars. Вызывается
            // метод save репозитория CarsRepository, представленного объетом repcars. В метод save передается
            // entity Cars представленная объектом detcars. Возвращаемое методом save значение содержит в себе
            // информацию о измененной (сохранённой) записи в таблицу cars и является объектом класса Cars (Entity).
            // Возвращаемое значение метода replaceCar(объект класса Cars) передается обратно в метод update в
            // объект pkk
            repcars.save(detcars);
            return detcars;
        }
        // если значение updCar (полученное из репозитория по Id, равному переменной carId) отсутствует возвращается
        // null
        return null;
    }
    /**
     * Метод deleteCar содержит в себе бизнес-логику API delcar, находящегося в контроллере CarsController.
     * Этот метод удаляет запись в таблице cars.
     * del - переменная типа long - содержит в себе данные переменной carIdDelete, переданной из метода delCar класса
     * (Контроллера) CarsController. Этот объект содержит id записи, которую следует удалить.
     */
    public void deleteCar(Long del) {
        // Здесь происходит вызов репозитория CarsRepository, представленного объектом repcars. Вызывается
        // метод deleteById репозитория CarsRepository, представленного объетом repcars. В метод deleteById передается
        // значение переменной del.
        repcars.deleteById(del);
    }
}