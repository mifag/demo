package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.BtrDto;
import com.mifag.app.demo.dto.Cat;
import com.mifag.app.demo.dto.TableOneDto;
import com.mifag.app.demo.entity.TableOne;
import com.mifag.app.demo.repository.TableOneRepository;
import com.mifag.app.demo.service.ServiceForFirstTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/table/one")
public class TableController {
    private final TableOneRepository history;
    private final ServiceForFirstTable keks;

    @Autowired
    public TableController(TableOneRepository tableOneRepository, ServiceForFirstTable keks) {
        this.history = tableOneRepository;
        this.keks = keks;
    }

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE, path = "/getRecords")
    public ResponseEntity<List<TableOne>>mylist() {
        Iterable<TableOne> records = history.findAll();
        List<TableOne> returnValue = new ArrayList<>();
        for (TableOne record : records) {
            Integer blat = keks.plusTwo(record.getAge());
            record.setAge(blat);
            returnValue.add(record);
        }
        return ResponseEntity.ok(returnValue);
    }

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE, path = "/getId")
    public ResponseEntity<TableOneDto> getTableOneId (@RequestParam(value = "num") Long tableId,
                                                      @RequestParam(value = "name") String poco) {
        TableOne aaa = history.getMeTableOneYouFuck(tableId, poco);
        mylist();

        Optional<TableOne> ista = history.findById(tableId) ;
        TableOne masc = null;
        TableOneDto tableCash = new TableOneDto();
        if (ista.isPresent()) {
            masc = ista.get();
            tableCash.setIdTable(masc.getId());
            String namedata = masc.getName();
            tableCash.setNameTable(namedata);
            tableCash.setAgeTable(masc.getAge());

        } else {
            masc = new TableOne();
        }

        return ResponseEntity.ok(tableCash);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TableOne> addTableOne() {

        TableOne table = new TableOne();
        table.setName("misha");
        table.setAge(50);
        TableOne createdTableOne = history.save(table);
        return ResponseEntity.ok(createdTableOne);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,path ="/btr")
    public ResponseEntity<Cat> addCat(@RequestBody BtrDto tiger) {
        TableOne bodytable = new  TableOne();
        bodytable.setName(tiger.getModel());
        bodytable.setAge(tiger.getPower());
        bodytable = history.save(bodytable);

        Cat rush = new Cat();
        rush.setName(bodytable.getName());
        rush.setHasTail(true);
        rush.setPaws(bodytable.getAge());
        return ResponseEntity.ok(rush);
    }


}
