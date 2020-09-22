package com.mifag.app.entity.specification;

import org.springframework.data.jpa.domain.Specification;

import com.mifag.app.entity.MidiKeyboard;

/**
 * @author <a href='mailto:mifag92@rambler.ru'>mifag</a>
 * @version 22.09.2020
 */
public final class MidiKeyboardSpecification extends SpecificationJoin {

    private static final String PERCENT = "%";

    /**
     * Find all.
     *
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findAll() {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.conjunction();
        };
    }

    /**
     * Find by id.
     *
     * @param id - midi-keyboard's id.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findById(Long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    /**
     * Find by manufacturer.
     *
     * @param manufacturer параметр.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findByManufacturer(String manufacturer) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("manufacturer")),
                PERCENT + manufacturer.toLowerCase() + PERCENT);
    }

    /**
     * Find by model.
     *
     * @param model - model of midi-keyboard.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findByModel(String model) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("model")),
                PERCENT + model.toLowerCase() + PERCENT);
    }

    /**
     * Find by keys number.
     *
     * @param keysNumber - keys.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findByKeys(Integer keysNumber) {
        return (root, query, cb) -> cb.equal(root.get("keysNumber"), keysNumber);
    }

    /**
     * Find by midiOut.
     *
     * @param hasMidiOut - Boolean.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findByMidiOut(Boolean hasMidiOut) {
        return (root, query, cb) -> cb.equal(root.get("hasMidiOut"), hasMidiOut);
    }

    /**
     * Find by price.
     *
     * @param price - midi-keyboard's price.
     * @return (root, query, cb).
     */
    public static Specification<MidiKeyboard> findByPrice(Long price) {
        return (root, query, cb) -> cb.equal(root.get("price"), price);
    }
}
