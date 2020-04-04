package com.mifag.app.demo.controller;

import com.mifag.app.demo.dto.Cat;
import com.mifag.app.demo.dto.TableTwoDto;
import com.mifag.app.demo.entity.TableTwo;
import com.mifag.app.demo.repository.TableTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/table/two")
public class TableControllerTwo {
    private final TableTwoRepository repository;

    @Autowired
    public TableControllerTwo(TableTwoRepository ass) {
        this.repository = ass;
    }

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRecords")
    public ResponseEntity<List<TableTwo>> getlist() {

        Iterable<TableTwo> records = repository.findAll();
        List<TableTwo> returnValue = new ArrayList<>();
        for (TableTwo record : records) {
            returnValue.add(record) ;

        }
        return ResponseEntity.ok(returnValue);

    }

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE, path = "/getById")
    public ResponseEntity<TableTwoDto> getTableTwoById (@RequestParam(value = "id") Long tableId) {
        Optional<TableTwo> cold = repository.findById(tableId) ;
        TableTwo hot = null;
        TableTwoDto tableData = new TableTwoDto();
        if (cold.isPresent()) {
            hot = cold.get();
            tableData.setIdOfTable(hot.getId());
            String nationalityData = hot.getNationality();
            tableData.setNationalityOfTable(nationalityData);
            tableData.setAgeOfTable(hot.getAge());
        } else {
            hot = new TableTwo();

        }
        return ResponseEntity.ok(tableData);

    }



    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TableTwo> addTableTwo() {

        String holl = "123";
        String ball = "153";
        TableTwo table = new TableTwo();
        table.setNationality("Anuta");
        table.setAge(100);
        TableTwo createdTableTwo = repository.save(table);
        createdTableTwo.setNationality(holl);

        dog(ball);

        dog(holl);

        System.out.println("hertw");

        return ResponseEntity.ok(createdTableTwo);

    }

    private void dog (String bat) {
        Cat catOne = new Cat() ;
        catOne.setPaws(4);
        catOne.setHasTail(true);
        catOne.setName("Tom");
        String a = "khe" ;
        Cat catTwo = new Cat(a) ;
        catTwo.setHasTail(false);
        Cat catThree = new Cat();
        catThree.setName (bat);



    }

}
