package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.exception.MidiKeyboardNotFoundException;
import com.mifag.app.demo.service.MidiKeyboardService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/midiKeyboard")
public class MidiKeyboardController {
    private final MidiKeyboardService midiService;

    @Autowired
    public MidiKeyboardController(MidiKeyboardService midiService) {
        this.midiService = midiService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MidiKeyboardDto> createMidiKeyboard(
            @RequestBody @Valid MidiKeyboardDto midiKeyboardReceived) {
        MidiKeyboardDto createdKeyboard = midiService.createMidi(midiKeyboardReceived);
        return ResponseEntity.ok(createdKeyboard);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getById")
    public ResponseEntity<MidiKeyboardDto> getMidiKeyboardById(@RequestParam(value = "id") Long midiId) {
        MidiKeyboardDto receivedMidiKeyboard = midiService.getMidiById(midiId);
        return ResponseEntity.ok(receivedMidiKeyboard);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRecords")
    public ResponseEntity<List<MidiKeyboardDto>> getAllMidiKeyboardRecords() {
        List<MidiKeyboardDto> midiKeyboardRecords = midiService.getAllMidiRecords();
        return ResponseEntity.ok(midiKeyboardRecords);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<MidiKeyboardDto> updateMidiKeyboard(@RequestBody @Valid MidiKeyboardDto midiBody,
                                                              @PathVariable(value = "midiId") Long updateMidiId) {
        MidiKeyboardDto updatedMidiKeyboard = midiService.updateMidiKeyboard(midiBody, updateMidiId);
        return ResponseEntity.ok(updatedMidiKeyboard);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<HttpStatus> deleteMidiKeyboard(@PathVariable(value = "midiId") Long carIdDelete) {
        midiService.deleteMidi(carIdDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/findByManufacturer")
    public  ResponseEntity<List<MidiKeyboardDto>> findByKeyboardManufacturer(
            @RequestParam(value = "name") String manufacturerName) {
        List<MidiKeyboardDto> foundKeyboards = midiService.findByManufacturer(manufacturerName);
        return ResponseEntity.ok(foundKeyboards);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByKeyNumber")
    public ResponseEntity<List<MidiKeyboardDto>> filterByKeyNumber(
            @RequestParam(value = "minKeys", required = false) Long minKeys,
            @RequestParam(value = "maxKeys", required = false) Long maxKeys,
            @RequestParam(value = "equalsKeys", required = false) Long equalsKeys) {
        List<MidiKeyboardDto> foundByKeys = midiService.findByKeys(minKeys, maxKeys, equalsKeys);
        return ResponseEntity.ok(foundByKeys);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByModel")
    public ResponseEntity<MidiKeyboardDto> filterByModel(
            @RequestParam(value = "name") String model) throws MidiKeyboardNotFoundException {
        MidiKeyboardDto foundByModel = midiService.findByModel(model);
        return ResponseEntity.ok(foundByModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPrice")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPrice(
            @RequestParam(value = "cost") Integer cost) {
        List<MidiKeyboardDto> foundByPrice = midiService.findByPrice(cost);
        return ResponseEntity.ok(foundByPrice);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByMidiOut")
    public ResponseEntity<List<MidiKeyboardDto>> filterByMidiOut(
            @RequestParam(value = "out") Boolean midiOut) {
        List<MidiKeyboardDto> foundByMidiOut = midiService.findByMidiOut(midiOut);
        return ResponseEntity.ok(foundByMidiOut);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPartOfManufacturer")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPartOfManufacturer(
            @RequestParam(value = "part") String partOfManufacturer) {
        List<MidiKeyboardDto> foundByPartOfManufacturer = midiService.findByPartOfManufacturer(partOfManufacturer);
        return ResponseEntity.ok(foundByPartOfManufacturer);
    }
}
// 1Сделать поиск по каждому из параметров.*
// 2Сделать поиск по комбинации параметров minKeys+equalsKeys *
// 3Сделать поиск по комбинации параметров maxKeys+equalsKeys *
// 4Почитать про синтаксис операторов if, for, else, else-if, логическое "и", логическое "или"
