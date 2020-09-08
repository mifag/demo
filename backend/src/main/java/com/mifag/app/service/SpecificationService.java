package com.mifag.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mifag.app.dto.SpecificationDto;
import com.mifag.app.entity.Specification;
import com.mifag.app.exception.SpecificationNotFoundException;
import com.mifag.app.repository.SpecificationRepository;

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

    /**
     * Search by id.
     *
     * @param specId - id.
     * @return found specification.
     * @throws SpecificationNotFoundException if specification with specific id not found.
     */
    public SpecificationDto getSpecificationById(Long specId) throws SpecificationNotFoundException {
        Optional<Specification> optionalSpecification = specificationRepository.findById(specId);
        SpecificationDto specificationDto = new SpecificationDto();
        if (optionalSpecification.isPresent()) {
            Specification specification = optionalSpecification.get();
            return new SpecificationDto(specification);
        }
        throw new SpecificationNotFoundException(specId);
    }
}
