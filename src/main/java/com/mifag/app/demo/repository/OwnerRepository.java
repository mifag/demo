package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Owner repository.
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    /**
     * Search by name.
     * @param ownerName .
     * @return found owners.
     */
    @Query("SELECT ow FROM Owner ow WHERE ow.name = :name")
    List<Owner> getByOwnerName(@Param("name") String ownerName);
}
