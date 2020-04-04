package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.Cars;
import org.springframework.data.repository.CrudRepository;

public interface CarsRepository extends CrudRepository<Cars, Long> {
}
