package com.mifag.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.mifag.app.entity.Specification;

/**
 * Specification repository.
 */
public interface SpecificationRepository extends CrudRepository<Specification, Long> {
}
