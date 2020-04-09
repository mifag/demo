package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.MidiKeyboard;
import com.mifag.app.demo.entity.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OwnerRepository extends CrudRepository<Owner, Long> {

    @Query("SELECT ow FROM Owner ow WHERE ow.name = :name")
    List<Owner> getByOwnerName(@Param("name") String ownerName);
}
