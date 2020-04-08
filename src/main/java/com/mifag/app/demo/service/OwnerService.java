package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.dto.OwnerDto;
import com.mifag.app.demo.entity.MidiKeyboard;
import com.mifag.app.demo.entity.Owner;
import com.mifag.app.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerService {
    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerDto createOwner(OwnerDto ownerData) {
        Owner ownerToCreate = new Owner(ownerData);
        Owner createdOwner = ownerRepository.save(ownerToCreate);
        return new OwnerDto(createdOwner);
    }
}
