package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.MidiKeyboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MidiKeyboardRepository extends CrudRepository<MidiKeyboard, Long> {

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer = :manufacturer")
    List<MidiKeyboard> getByManufacturerName(@Param("manufacturer") String manufacturerName);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min AND mk.keysNumber < :max")
    List<MidiKeyboard> getByMinKeysAndMaxKeys(@Param("min") Integer min,
                                              @Param("max") Integer max);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMinKeysAndEqualsKeys(@Param("min") Integer min,
                                                 @Param("equals") Integer equals);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber < :max OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMaxKeysAndEqualsKeys(@Param("max") Integer max,
                                                 @Param("equals") Integer equals);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.model = :model")
    MidiKeyboard getByModel(@Param("model") String model);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.price = :costKey")
    List<MidiKeyboard> getByPrice(@Param("costKey") Long costKey);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.hasMidiOut = :midiOut")
    List<MidiKeyboard> getByMidiOut(@Param("midiOut") Boolean out);

    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer LIKE %:part% ")
    List<MidiKeyboard> getByPartOfManufacturer(@Param("part") String part);
}
