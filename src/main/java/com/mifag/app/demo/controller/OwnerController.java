package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.OwnerDto;
import com.mifag.app.demo.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDto> createOwner(
            @RequestBody @Valid OwnerDto ownerReceived) {
        LOG.info("Мы вошли в метод Post. Сохраняем пользователя с именем {}", ownerReceived.getName());
        OwnerDto createdOwner = ownerService.createOwner(ownerReceived);
        LOG.info("Пользователь с именем {} успешно сохранен. Id в базе данных: {}", ownerReceived.getName(),
                createdOwner.getId());
        return ResponseEntity.ok(createdOwner);
    }
}

// сделать все api (Все записи, одну запись по id, записи по имени, редактирование, удаление) + логи(вход-выход) 