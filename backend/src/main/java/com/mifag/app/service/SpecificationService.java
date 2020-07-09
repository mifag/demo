package com.mifag.app.service;

import com.mifag.app.entity.Specification;
import com.mifag.app.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Specification service.
 */
@Component
public class SpecificationService {

    private final SpecificationRepository specificationRepository;

    @Autowired
    public SpecificationService(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    public Specification saveSpecification(Specification specification) {
        return specificationRepository.save(specification);
    }
}
