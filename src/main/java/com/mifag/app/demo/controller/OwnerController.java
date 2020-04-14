package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.OwnerDto;
import com.mifag.app.demo.exception.MidiKeyboardNotFoundException;
import com.mifag.app.demo.exception.OwnerNotFoundException;
import com.mifag.app.demo.service.OwnerService;
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
@RequestMapping("/owner")
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Post-запрос по протоколу http. Создание нового владельца.
     * @param ownerReceived - данные нового владельца.
     * @return созданный владелец.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия данных миди-клавитуры.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDto> createOwner(
            @RequestBody @Valid OwnerDto ownerReceived) throws MidiKeyboardNotFoundException {
        LOG.info("Мы вошли в метод createOwner. Сохраняем пользователя с именем {}", ownerReceived.getName());
        OwnerDto createdOwner = ownerService.createOwner(ownerReceived);
        LOG.info("Пользователь с именем {} успешно сохранен. Id в базе данных: {}", ownerReceived.getName(),
                createdOwner.getId());
        return ResponseEntity.ok(createdOwner);
    }

    /**
     * Get-запрос по протоколу http.
     * @param idOwner - поиск владельца по id.
     * @return данные владельца с запрошенным id.
     * @throws OwnerNotFoundException отправляется на клиент в случае отсутствия владельца с данным id
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/ownerById")
    public ResponseEntity<OwnerDto> getOwnerById(@RequestParam(value = "id") Long idOwner)
            throws OwnerNotFoundException {
        LOG.info("Мы вошли в метод getOwnerById. Получаем данные пользователя с id {}", idOwner);
        OwnerDto receivedOwner = ownerService.getOwnerById(idOwner);
        LOG.info("Пользователь с id {} успешно найден. Имя пользователя в базе данных: {}", idOwner,
                receivedOwner.getName());
        return ResponseEntity.ok(receivedOwner);
    }

    /**
     * Get-запрос по протоколу http. Поиск данных всех владельцев.
     * @return данные всех владельцев.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/allOwners")
    public ResponseEntity<List<OwnerDto>> getOwnerRecords() {
        LOG.info("Мы вошли в метод getOwnerRecords. Получаем данные всех пользователей");
        List<OwnerDto> ownerRecords = ownerService.getAllOwnerRecords();
        LOG.info("Данные всех пользователей успешно получены");
        return ResponseEntity.ok(ownerRecords);
    }

    /**
     * Put-запрос по протоколу http. Замена записи о владельце с запрошенным id.
     * @param owner - параметры нового владельца.
     * @param ownerId- id владельца, данные которого следует заменить.
     * @return данные нового пользователя.
     * @throws OwnerNotFoundException отправляется на клиент в случае отсутствия владельца с данным id.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия данных миди-клавиатуры.
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{ownerId}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody @Valid OwnerDto owner,
                                                @PathVariable(value = "ownerId") Long ownerId)
            throws OwnerNotFoundException, MidiKeyboardNotFoundException {
        LOG.info("Мы вошли в метод updateOwner. Заменяем данные пользователя с id: {} на данные " +
                "нового пользователя c именем {}", ownerId, owner.getName());
        OwnerDto updatedOwner = ownerService.updateOwner(owner, ownerId);
        LOG.info("Пользователь с id {} успешно изменен. Имя в базе данных: {}", updatedOwner.getId(),
                updatedOwner.getName());
        return ResponseEntity.ok(updatedOwner);
    }

    /**
     * Get-запрос по протоколу http.
     * @param ownerName - поиск владельцев по имени.
     * @return найденные владельцы.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/findByOwner")
    public  ResponseEntity<List<OwnerDto>> findByOwner(
            @RequestParam(value = "name") String ownerName) {
        LOG.info("Мы вошли в метод findByOwner. Получаем данные пользователей с именем {}", ownerName);
        List<OwnerDto> foundOwners = ownerService.findByOwnerName(ownerName);
        LOG.info("Пользователи с именем {} успешно найдены.", ownerName);
        return ResponseEntity.ok(foundOwners);
    }

    /**
     * Delete-запрос по протоколу http.
     * @param idOwner - удаление данных владельца по заданному id.
     * @return http статус на клиент.
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{ownerId}")
    public ResponseEntity<HttpStatus> deleteOwner(@PathVariable(value = "ownerId") Long idOwner) {
        LOG.info("Мы вошли в метод deleteOwner. Удаляем данные пользователя с id {}", idOwner);
        ownerService.deleteOwner(idOwner);
        LOG.info("Пользователь с id {} успешно удален", idOwner);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
