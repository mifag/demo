package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.Owner;
import org.springframework.data.repository.CrudRepository;


public interface OwnerRepository extends CrudRepository<Owner, Long> {

}
