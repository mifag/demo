package com.mifag.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mifag.app.dto.SpecificationDto;
import com.mifag.app.exception.SpecificationNotFoundException;
import com.mifag.app.service.SpecificationService;

/**
 * @author <a href='mailto:mifag92@rambler.ru'>mifag</a>
 * @version 03.09.2020
 */

@RestController

@RequestMapping("/api/specification")
public class SpecificationController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final SpecificationService specificationService;

    /**
     * Constructor.
     *
     * @param specificationService - specification service.
     */
    @Autowired
    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    /**
     * Get Specification by id.
     *
     * @param specId - specification id.
     * @return specification dto.
     * @throws SpecificationNotFoundException if specification not found.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{specId}")
    public ResponseEntity<SpecificationDto> getSpecificationById(@PathVariable(value = "specId") Long specId)
            throws SpecificationNotFoundException {
        LOG.info("SpecificationController. GetSpecificationById. Finding specification with Id: {}.", specId);
        SpecificationDto specificationDto = specificationService.getSpecificationById(specId);
        LOG.info("Specification with id: {} successfully found.", specId);
        return ResponseEntity.ok(specificationDto);
    }
}
