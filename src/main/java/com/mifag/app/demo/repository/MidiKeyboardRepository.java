package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.MidiKeyboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Репозиторий миди-клавиатур.
 */
public interface MidiKeyboardRepository extends CrudRepository<MidiKeyboard, Long> {
    /**
     * Поиск в базе данных миди-клавиатуры по производителю.
     * @param manufacturerName - имя производителя.
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer = :manufacturer")
    List<MidiKeyboard> getByManufacturerName(@Param("manufacturer") String manufacturerName);

    /**
     * Поиск в базе данных миди-клавиатуры с заданными минимальными и максимальными значениями количества клавиш.
     * @param min - минимальное количество клавиш.
     * @param max - максимальное количество клавиш.
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min AND mk.keysNumber < :max")
    List<MidiKeyboard> getByMinKeysAndMaxKeys(@Param("min") Integer min,
                                              @Param("max") Integer max);

    /**
     * Поиск в базе данных миди-клавиатуры с заданными минимальными или равными equals значениями количества клавиш
     * @param min - минимальное количество клавиш.
     * @param equals - заданное количество клавиш.
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMinKeysAndEqualsKeys(@Param("min") Integer min,
                                                 @Param("equals") Integer equals);

    /**
     * Поиск в базе данных миди-клавиатуры с заданными максимальными или равными equals значениями количества клавиш
     * @param max - максимальное количество клавиш.
     * @param equals - заданное количество клавиш.
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber < :max OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMaxKeysAndEqualsKeys(@Param("max") Integer max,
                                                 @Param("equals") Integer equals);

    /**
     * Поиск в базе данных миди-клавиатуры по модели.
     * @param model .
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.model = :model")
    MidiKeyboard getByModel(@Param("model") String model);

    /**
     * Поиск в базе данных миди-клавиатуры по цене.
     * @param costKey .
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.price = :costKey")
    List<MidiKeyboard> getByPrice(@Param("costKey") Long costKey);

    /**
     * Поиск в базе данных миди-клавиатуры по наличию миди-выхода.
     * @param out .
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.hasMidiOut = :midiOut")
    List<MidiKeyboard> getByMidiOut(@Param("midiOut") Boolean out);

    /**
     * Поиск в базе данных миди-клавиатуры по части названия производителя.
     * @param part .
     * @return найденные миди-клавиатуры.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer LIKE %:part% ")
    List<MidiKeyboard> getByPartOfManufacturer(@Param("part") String part);
}
