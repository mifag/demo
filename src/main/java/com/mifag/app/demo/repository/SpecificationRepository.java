package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий спецификаций.
 */
public interface SpecificationRepository extends CrudRepository<Specification, Long> {
}
