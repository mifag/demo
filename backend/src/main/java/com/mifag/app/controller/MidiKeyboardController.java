package com.mifag.app.controller;

import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mifag.app.dto.MidiKeyboardDto;
import com.mifag.app.exception.MidiKeyboardNotFoundException;
import com.mifag.app.service.MidiKeyboardService;

/**
 * @author <a href='mailto:mifag92@rambler.ru'>mifag</a>
 * @version 03.09.2020
 */

@RestController

@RequestMapping("/api/midiKeyboard")
public class MidiKeyboardController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final MidiKeyboardService midiService;

    /**
     * Constructor.
     *
     * @param midiService - midi-keyboard service.
     */
    @Autowired
    public MidiKeyboardController(MidiKeyboardService midiService) {
        this.midiService = midiService;
    }

    /**
     * Create midi keyboard.
     *
     * @param midiKeyboardReceived midi keyboard dto.
     * @return new dto.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MidiKeyboardDto> createMidiKeyboard(
            @RequestBody @Valid MidiKeyboardDto midiKeyboardReceived) {
        LOG.info("MidiKeyboardController. CreateMidiKeyboard. Saving midi keyboard of manufacturer: {}, model: {}.",
                midiKeyboardReceived.getManufacturer(), midiKeyboardReceived.getModel());
        MidiKeyboardDto createdKeyboard = midiService.createMidi(midiKeyboardReceived);
        LOG.info("Midi keyboard {} successfully save. Id: {}.",
                midiKeyboardReceived.getModel(), createdKeyboard.getId());
        return ResponseEntity.ok(createdKeyboard);
    }

    /**
     * Search midi by id.
     *
     * @param midiId - id.
     * @return midi keyboard dto.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<MidiKeyboardDto> getMidiKeyboardById(@PathVariable(value = "midiId") Long midiId)
            throws MidiKeyboardNotFoundException {
        LOG.info("MidiKeyboardController. GetMidiKeyboardById. Finding midi keyboard with Id: {}.", midiId);
        MidiKeyboardDto receivedMidiKeyboard = midiService.getMidiById(midiId);
        LOG.info("Midi keyboard with id: {} successfully found. Model: {}.", midiId,
                receivedMidiKeyboard.getModel());
        return ResponseEntity.ok(receivedMidiKeyboard);
    }

    /**
     * Search all midi keyboards.
     *
     * @return all midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/allRecords")
    public ResponseEntity<List<MidiKeyboardDto>> getAllMidiKeyboardRecords() {
        LOG.info("MidiKeyboardController. getAllMidiKeyboardRecords. Getting data of all midi keyboards.");
        List<MidiKeyboardDto> midiKeyboardRecords = midiService.getAllMidiRecords();
        LOG.info("All midi keyboards successfully found.");
        //midiKeyboardRecords.sort(Comparator.comparing(MidiKeyboardDto::getId));
        midiKeyboardRecords.sort(Comparator.comparing(MidiKeyboardDto::getId));
        return ResponseEntity.ok(midiKeyboardRecords);
    }

    /**
     * Replace midi keyboard with specific id in database.
     *
     * @param midiBody     - dto of new midi keyboard.
     * @param updateMidiId - replaceable midi keyboard's id.
     * @return new midi keyboard.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<MidiKeyboardDto> updateMidiKeyboard(@RequestBody @Valid MidiKeyboardDto midiBody,
                                                              @PathVariable(value = "midiId") Long updateMidiId)
            throws MidiKeyboardNotFoundException {
        LOG.info("MidiKeyboardController. UpdateMidiKeyboard. Changing midi keyboard with id: {} " +
                "to new midi keyboard: {}.", updateMidiId, midiBody.getModel());
        MidiKeyboardDto updatedMidiKeyboard = midiService.updateMidiKeyboard(midiBody, updateMidiId);
        LOG.info("Midi keyboard with id: {} successfully changed. New model: {}.",
                updatedMidiKeyboard.getId(), updatedMidiKeyboard.getModel());
        return ResponseEntity.ok(updatedMidiKeyboard);
    }

    /**
     * Delete midi keyboard with specific id.
     *
     * @param midiIdDelete - id.
     * @return http status.
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<HttpStatus> deleteMidiKeyboard(@PathVariable(value = "midiId") Long midiIdDelete) {
        LOG.info("MidiKeyboardController. DeleteMidiKeyboard. Deleting midi keyboard with id: {}.", midiIdDelete);
        midiService.deleteMidi(midiIdDelete);
        LOG.info("Midi keyboard with id: {} successfully deleted.", midiIdDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /**
     * Search midi keyboards by manufacturer name.
     *
     * @param manufacturerName .
     * @return found midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/findByManufacturer")
    public ResponseEntity<List<MidiKeyboardDto>> findByKeyboardManufacturer(
            @RequestParam(value = "name") String manufacturerName) {
        LOG.info("MidiKeyboardController. FindByKeyboardManufacturer. Finding midi keyboard by " +
                "manufacturer: {}.", manufacturerName);
        List<MidiKeyboardDto> foundKeyboards = midiService.findByManufacturer(manufacturerName);
        LOG.info("Midi keyboards by manufacturer: {} successfully found.", manufacturerName);
        return ResponseEntity.ok(foundKeyboards);
    }

    /**
     * Search midi keyboards by number of key.
     *
     * @param minKeys    .
     * @param maxKeys    .
     * @param equalsKeys .
     * @return found midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByKeyNumber")
    public ResponseEntity<List<MidiKeyboardDto>> filterByKeyNumber(
            @RequestParam(value = "minKeys", required = false) Long minKeys,
            @RequestParam(value = "maxKeys", required = false) Long maxKeys,
            @RequestParam(value = "equalsKeys", required = false) Long equalsKeys) {
        LOG.info("MidiKeyboardController. FilterByKeyNumber.");
        List<MidiKeyboardDto> foundByKeys = midiService.findByKeys(minKeys, maxKeys, equalsKeys);
        LOG.info("Midi keyboards successfully found.");
        return ResponseEntity.ok(foundByKeys);
    }

    /**
     * Search midi keyboards by model.
     *
     * @param model .
     * @return found midi keyboard.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific model not found.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByModel")
    public ResponseEntity<MidiKeyboardDto> filterByModel(
            @RequestParam(value = "name") String model) throws MidiKeyboardNotFoundException {
        LOG.info("MidiKeyboardController. FilterByModel.");
        MidiKeyboardDto foundByModel = midiService.findByModel(model);
        LOG.info("Midi keyboard successfully found.");
        return ResponseEntity.ok(foundByModel);
    }

    /**
     * Search midi keyboards by cost.
     *
     * @param cost .
     * @return found midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPrice")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPrice(
            @RequestParam(value = "cost") Integer cost) {
        LOG.info("MidiKeyboardController. FilterByPrice.");
        List<MidiKeyboardDto> foundByPrice = midiService.findByPrice(cost);
        LOG.info("Midi keyboards successfully found.");
        return ResponseEntity.ok(foundByPrice);
    }

    /**
     * Search midi keyboards by presence of midi out.
     *
     * @param midiOut - the presence of midi output.
     * @return found midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByMidiOut")
    public ResponseEntity<List<MidiKeyboardDto>> filterByMidiOut(
            @RequestParam(value = "out") Boolean midiOut) {
        LOG.info("MidiKeyboardController. FilterByMidiOut.");
        List<MidiKeyboardDto> foundByMidiOut = midiService.findByMidiOut(midiOut);
        LOG.info("Midi keyboards successfully found.");
        return ResponseEntity.ok(foundByMidiOut);
    }

    /**
     * Search midi keyborads by part of manufacturer name.
     *
     * @param partOfManufacturer .
     * @return found midi keyboards.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPartOfManufacturer")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPartOfManufacturer(
            @RequestParam(value = "part") String partOfManufacturer) {
        LOG.info("MidiKeyboardController. FilterByPartOfManufacturer.");
        List<MidiKeyboardDto> foundByPartOfManufacturer = midiService.findByPartOfManufacturer(partOfManufacturer);
        LOG.info("Midi keyboards successfully found.");
        return ResponseEntity.ok(foundByPartOfManufacturer);
    }

    /**
     * Search midi-keyboard.
     *
     * @param midiKeyboardSearch - dto for search.
     * @return found midi-keyboards.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/search")
    public ResponseEntity<List<MidiKeyboardDto>> searchMidiKeyboard(@RequestBody MidiKeyboardDto midiKeyboardSearch) {
        List<MidiKeyboardDto> foundKeyboards = midiService.search(midiKeyboardSearch);
        return ResponseEntity.ok(foundKeyboards);
    }
}
