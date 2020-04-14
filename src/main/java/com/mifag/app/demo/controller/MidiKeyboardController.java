package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.exception.MidiKeyboardNotFoundException;
import com.mifag.app.demo.service.MidiKeyboardService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller для связи с клиентом по протоколу http.
 */
@RestController
@RequestMapping("/midiKeyboard")
public class MidiKeyboardController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final MidiKeyboardService midiService;

    @Autowired
    public MidiKeyboardController(MidiKeyboardService midiService) {
        this.midiService = midiService;
    }

    /**
     * Post-запрос по протоколу http. Создание новой мидиклавиатуры.
     * @param midiKeyboardReceived - параметры новой клавиатуры.
     * @return данные о новой миди-клавиатуре.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MidiKeyboardDto> createMidiKeyboard(
            @RequestBody @Valid MidiKeyboardDto midiKeyboardReceived) {
        LOG.info("Мы вошли в метод createMidiKeyboard. Сохраняем миди-клавиатуру производителя {} и модели {}",
                midiKeyboardReceived.getManufacturer(), midiKeyboardReceived.getModel());
        MidiKeyboardDto createdKeyboard = midiService.createMidi(midiKeyboardReceived);
        LOG.info("Миди-клавиатура модели {} успешно сохранена. Id в базе данных: {}",
                midiKeyboardReceived.getModel(), createdKeyboard.getId());
        return ResponseEntity.ok(createdKeyboard);
    }

    /**
     * Get-запрос по протоколу http.
     * @param midiId - поиск миди-клавиатуры по id.
     * @return миди-клавиатура с запрошенным id.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия объекта с данным id.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getById")
    public ResponseEntity<MidiKeyboardDto> getMidiKeyboardById(@RequestParam(value = "id") Long midiId)
            throws MidiKeyboardNotFoundException {
        LOG.info("Мы вошли в метод getMidiKeyboardById. Получаем данные миди-клавиатуры с Id: {}", midiId);
                MidiKeyboardDto receivedMidiKeyboard = midiService.getMidiById(midiId);
        LOG.info("Миди-клавиатура с id {} успешно найдена. Модель клавиатуры в базе данных: {}", midiId,
                receivedMidiKeyboard.getModel());
        return ResponseEntity.ok(receivedMidiKeyboard);
    }

    /**
     * Get-запрос по протоколу http. Поиск всех миди-клавиатур.
     * @return данные всех миди-клавиатур.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRecords")
    public ResponseEntity<List<MidiKeyboardDto>> getAllMidiKeyboardRecords() {
        LOG.info("Мы вошли в метод getAllMidiKeyboardRecords. Получаем данные всех миди-клавиатур");
        List<MidiKeyboardDto> midiKeyboardRecords = midiService.getAllMidiRecords();
        LOG.info("Данные всех миди-клавиатур успешно получены");
        return ResponseEntity.ok(midiKeyboardRecords);
    }

    /**
     * Put-запрос по протоколу http. Замена записи о миди-клавиатуре с запрошенным id.
     * @param midiBody - параметры новой клавиатуры.
     * @param updateMidiId - id клавиатуры, которую следует заменить.
     * @return новая миди-клавиатура с запрошенным id.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия объекта с данным id.
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<MidiKeyboardDto> updateMidiKeyboard(@RequestBody @Valid MidiKeyboardDto midiBody,
                                                              @PathVariable(value = "midiId") Long updateMidiId)
            throws MidiKeyboardNotFoundException {
        LOG.info("Мы вошли в метод updateMidiKeyboard. Заменяем данные миди-клавиатуры с id: {} " +
                "на данные новой мидиклавиатуры модели {}", updateMidiId, midiBody.getModel());
        MidiKeyboardDto updatedMidiKeyboard = midiService.updateMidiKeyboard(midiBody, updateMidiId);
        LOG.info("Миди-клавиатура с id {} успешно изменена. Модель в базе данных: {}",
                updatedMidiKeyboard.getId(), updatedMidiKeyboard.getModel());
        return ResponseEntity.ok(updatedMidiKeyboard);
    }

    /**
     * Delete-запрос по протоколу http.
     * @param midiIdDelete - удаление миди-клавиатуры по заданному id.
     * @return http статус на клиент.
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{midiId}")
    public ResponseEntity<HttpStatus> deleteMidiKeyboard(@PathVariable(value = "midiId") Long midiIdDelete) {
        LOG.info("Мы вошли в метод deleteMidiKeyboard. Удаляем данные миди-клавиатуры с id {}", midiIdDelete);
        midiService.deleteMidi(midiIdDelete);
        LOG.info("Миди-клавиатура с id {} успешно удалена", midiIdDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /**
     * Get-запрос по протоколу http.
     * @param manufacturerName - поиск миди-клавиатур по производителю.
     * @return найденные клавиатуры.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/findByManufacturer")
    public  ResponseEntity<List<MidiKeyboardDto>> findByKeyboardManufacturer(
            @RequestParam(value = "name") String manufacturerName) {
        LOG.info("Мы вошли в метод findByKeyboardManufacturer. Получаем данные миди-клавиатур с " +
                "производителем {}", manufacturerName);
        List<MidiKeyboardDto> foundKeyboards = midiService.findByManufacturer(manufacturerName);
        LOG.info("Миди-клавиатуры с производителем {} успешно найдены.", manufacturerName);
        return ResponseEntity.ok(foundKeyboards);
    }

    /**
     * Get-запрос по протоколу http. Поиск миди-клавиатур по количеству клавиш
     * @param minKeys - минимальное количество клавиш.
     * @param maxKeys - максимальное количество клавиш.
     * @param equalsKeys - заданное количество клавиш.
     * @return найденные клавиатуры.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByKeyNumber")
    public ResponseEntity<List<MidiKeyboardDto>> filterByKeyNumber(
            @RequestParam(value = "minKeys", required = false) Long minKeys,
            @RequestParam(value = "maxKeys", required = false) Long maxKeys,
            @RequestParam(value = "equalsKeys", required = false) Long equalsKeys) {
        LOG.info("Мы вошли в метод filterByKeyNumber. Получаем данные миди-клавиатур по количеству клавиш");
                List<MidiKeyboardDto> foundByKeys = midiService.findByKeys(minKeys, maxKeys, equalsKeys);
        LOG.info("Миди-клавиатуры по количеству клавиш успешно найдены.");
        return ResponseEntity.ok(foundByKeys);
    }

    /**
     * Get-запрос по протоколу http.
     * @param model - поиск миди-клавиатуры по модели.
     * @return найденная модель.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия модели в базе данных.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByModel")
    public ResponseEntity<MidiKeyboardDto> filterByModel(
            @RequestParam(value = "name") String model) throws MidiKeyboardNotFoundException {
        LOG.info("Мы вошли в метод filterByModel. Получаем данные миди-клавиатуры модели {}", model);
        MidiKeyboardDto foundByModel = midiService.findByModel(model);
        LOG.info("Миди-клавиатура модели {} успешно найдена.", model);
        return ResponseEntity.ok(foundByModel);
    }

    /**
     * Get-запрос по протоколу http.
     * @param cost - поиск миди-клавиатур по цене.
     * @return найденные миди-клавиатуры.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPrice")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPrice(
            @RequestParam(value = "cost") Integer cost) {
        LOG.info("Мы вошли в метод filterByPrice. Получаем данные миди-клавиатур по цене {}", cost);
        List<MidiKeyboardDto> foundByPrice = midiService.findByPrice(cost);
        LOG.info("Миди-клавиатуры по цене {} успешно найдены.", cost);
        return ResponseEntity.ok(foundByPrice);
    }

    /**
     * Get-запрос по протоколу http.
     * @param midiOut - поиск миди-клавиатур по наличию миди-выхода.
     * @return найденные миди-клавиатуры.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByMidiOut")
    public ResponseEntity<List<MidiKeyboardDto>> filterByMidiOut(
            @RequestParam(value = "out") Boolean midiOut) {
        LOG.info("Мы вошли в метод filterByMidiOut. " +
                "Получаем данные миди-клавиатур со значением миди-выхода {}", midiOut);
        List<MidiKeyboardDto> foundByMidiOut = midiService.findByMidiOut(midiOut);
        LOG.info("Миди-клавиатуры со значением миди-выхода {} успешно найдены.", midiOut);
        return ResponseEntity.ok(foundByMidiOut);
    }

    /**
     * Get-запрос по протоколу http.
     * @param partOfManufacturer - поиск миди-клавиатур по части имени производителя.
     * @return найденные миди-клавиатуры.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/filterByPartOfManufacturer")
    public ResponseEntity<List<MidiKeyboardDto>> filterByPartOfManufacturer(
            @RequestParam(value = "part") String partOfManufacturer) {
        LOG.info("Мы вошли в метод filterByPartOfManufacturer. Получаем данные миди-клавиатур по части имени " +
                "производителя {}", partOfManufacturer);
        List<MidiKeyboardDto> foundByPartOfManufacturer = midiService.findByPartOfManufacturer(partOfManufacturer);
        LOG.info("Данные миди-клавиатур по части имени производителя {} успешно получены", partOfManufacturer);
        return ResponseEntity.ok(foundByPartOfManufacturer);
    }
}
