package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.OwnerMidiKeyboardMap;
import org.springframework.data.repository.CrudRepository;

/**
 * Карта связей владельца и миди-клавиатуры.
 */
public interface OwnerMidiKeyboardMapRepository extends CrudRepository<OwnerMidiKeyboardMap, Long> {

}
