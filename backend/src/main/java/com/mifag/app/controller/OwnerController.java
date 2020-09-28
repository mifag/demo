package com.mifag.app.controller;

import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mifag.app.dto.MidiKeyboardDto;
import com.mifag.app.dto.OwnerDto;
import com.mifag.app.exception.MidiKeyboardNotFoundException;
import com.mifag.app.exception.OwnerNotFoundException;
import com.mifag.app.service.OwnerService;

/**
 * Owner controller.
 */
@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Create owner.
     *
     * @param ownerReceived - new owner.
     * @return created owner.
     * @throws MidiKeyboardNotFoundException if that owner hasn't midi keyboard.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDto> createOwner(
            @RequestBody @Valid OwnerDto ownerReceived) throws MidiKeyboardNotFoundException {
        LOG.info("OwnerController. CreateOwner. Creating owners with name: {}.", ownerReceived.getName());
        OwnerDto createdOwner = ownerService.createOwner(ownerReceived);
        LOG.info("Owner with name: {} successfully saved. Id: {}.", ownerReceived.getName(),
                createdOwner.getId());
        return ResponseEntity.ok(createdOwner);
    }

    /**
     * Search owner by id.
     *
     * @param idOwner .
     * @return found owner.
     * @throws OwnerNotFoundException if owner with specific id not found.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable(value = "id") Long idOwner)
            throws OwnerNotFoundException {
        LOG.info("OwnerController. GetOwnerById. Finding owner with id {}.", idOwner);
        OwnerDto receivedOwner = ownerService.getOwnerById(idOwner);
        LOG.info("Owner with id {} successfully found. Name: {}.", idOwner,
                receivedOwner.getName());
        return ResponseEntity.ok(receivedOwner);
    }

    /**
     * Search all owners.
     *
     * @return all owners.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OwnerDto>> getOwnerRecords() {
        LOG.info("OwnerController. GetOwnerRecords.");
        List<OwnerDto> ownerRecords = ownerService.getAllOwnerRecords();
        LOG.info("All owners successfully found.");
        ownerRecords.sort(Comparator.comparing(OwnerDto::getId));
        return ResponseEntity.ok(ownerRecords);
    }

    /**
     * Replace owner with specific id.
     *
     * @param owner   - new owner.
     * @param ownerId old owner id.
     * @return new owner dto.
     * @throws OwnerNotFoundException        if owner with specific id not found.
     * @throws MidiKeyboardNotFoundException if that owner hasn't midi keyboard.
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{ownerId}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody @Valid OwnerDto owner,
                                                @PathVariable(value = "ownerId") Long ownerId)
            throws OwnerNotFoundException, MidiKeyboardNotFoundException {
        LOG.info("OwnerController. UpdateOwner. Replacing owner with id: {} " +
                "to new owner with name: {}.", ownerId, owner.getName());
        OwnerDto updatedOwner = ownerService.updateOwner(owner, ownerId);
        LOG.info("Owner with id: {} successfully replaced. Name: {}.", updatedOwner.getId(),
                updatedOwner.getName());
        return ResponseEntity.ok(updatedOwner);
    }

    /**
     * Search owner by name.
     *
     * @param ownerName .
     * @return found owners.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/findByOwner")
    public ResponseEntity<List<OwnerDto>> findByOwner(
            @RequestParam(value = "name") String ownerName) {
        LOG.info("OwnerController. FindByOwner. Search owner by name: {}.", ownerName);
        List<OwnerDto> foundOwners = ownerService.findByOwnerName(ownerName);
        LOG.info("Owners with name: {} successfully found.", ownerName);
        return ResponseEntity.ok(foundOwners);
    }

    /**
     * Delete owner with specific id.
     *
     * @param idOwner .
     * @return http status.
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{ownerId}")
    public ResponseEntity<HttpStatus> deleteOwner(@PathVariable(value = "ownerId") Long idOwner) {
        LOG.info("OwnerController. DeleteOwner. Deleting owner with id: {}.", idOwner);
        ownerService.deleteOwner(idOwner);
        LOG.info("Owner with id: {} successfully deleted.", idOwner);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
