package com.mifag.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mifag.app.entity.MidiKeyboard;

/**
 * Midi keyboard repository.
 */
public interface MidiKeyboardRepository extends CrudRepository<MidiKeyboard, Long>,
        JpaSpecificationExecutor<MidiKeyboard> {
    /**
     * Search by manufacturer.
     *
     * @param manufacturerName .
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer = :manufacturer")
    List<MidiKeyboard> getByManufacturerName(@Param("manufacturer") String manufacturerName);

    /**
     * Search by number of keys.
     *
     * @param min number of keys.
     * @param max number of keys.
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min AND mk.keysNumber < :max")
    List<MidiKeyboard> getByMinKeysAndMaxKeys(@Param("min") Integer min,
                                              @Param("max") Integer max);

    /**
     * Search by number of keys.
     *
     * @param min    number of keys.
     * @param equals number of keys.
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber > :min OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMinKeysAndEqualsKeys(@Param("min") Integer min,
                                                 @Param("equals") Integer equals);

    /**
     * Search by number of keys.
     *
     * @param max    number of keys.
     * @param equals number of keys.
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.keysNumber < :max OR mk.keysNumber = :equals")
    List<MidiKeyboard> getByMaxKeysAndEqualsKeys(@Param("max") Integer max,
                                                 @Param("equals") Integer equals);

    /**
     * Search by model.
     *
     * @param model .
     * @return found midi keyboard.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.model = :model")
    MidiKeyboard getByModel(@Param("model") String model);

    /**
     * Search by keyboard cost.
     *
     * @param costKey .
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.price = :costKey")
    List<MidiKeyboard> getByPrice(@Param("costKey") Long costKey);

    /**
     * Search by presence of midi out.
     *
     * @param out - the presence of midi output.
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.hasMidiOut = :midiOut")
    List<MidiKeyboard> getByMidiOut(@Param("midiOut") Boolean out);

    /**
     * Search by part of manufacturer name.
     *
     * @param part - part of manufacturer name.
     * @return found midi keyboards.
     */
    @Query("SELECT mk FROM MidiKeyboard mk WHERE mk.manufacturer LIKE %:part% ")
    List<MidiKeyboard> getByPartOfManufacturer(@Param("part") String part);
}
