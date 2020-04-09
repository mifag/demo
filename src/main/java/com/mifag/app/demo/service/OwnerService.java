package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.dto.OwnerDto;
import com.mifag.app.demo.entity.MidiKeyboard;
import com.mifag.app.demo.entity.Owner;
import com.mifag.app.demo.entity.OwnerMidiKeyboardMap;
import com.mifag.app.demo.exception.MidiKeyboardNotFoundException;
import com.mifag.app.demo.exception.OwnerNotFoundException;
import com.mifag.app.demo.repository.OwnerMidiKeyboardMapRepository;
import com.mifag.app.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final MidiKeyboardService midiKeyboardService;
    private final OwnerMidiKeyboardMapRepository ownerMidiKeyboardMapRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, MidiKeyboardService midiKeyboardService,
                        OwnerMidiKeyboardMapRepository ownerMidiKeyboardMapRepository) {
        this.ownerRepository = ownerRepository;
        this.midiKeyboardService = midiKeyboardService;
        this.ownerMidiKeyboardMapRepository = ownerMidiKeyboardMapRepository;
    }

    public OwnerDto createOwner(OwnerDto ownerData) throws MidiKeyboardNotFoundException {
        Owner ownerToCreate = new Owner(ownerData);
        Owner createdOwner = ownerRepository.save(ownerToCreate);
        //получить List<midiKeyboardDto> из ownerData
        List<MidiKeyboardDto> midiKeyboardDtoList = ownerData.getMidiKeyboardList();
        List<MidiKeyboardDto> ownerKeyboard = new ArrayList<>();
        //вытащить записи midiKeyboardDto из List<>
        for (MidiKeyboardDto midiKeyboardDto : midiKeyboardDtoList) {
            //обратиться в midiKeyboardService и передать туда по одной записи midiKeyboardDto
            MidiKeyboardDto idDto = midiKeyboardService.createMidi(midiKeyboardDto);
            ownerKeyboard.add(idDto);
            Long midiKeyboardId = idDto.getId();
            MidiKeyboard midiKeyboard = midiKeyboardService.getMidiKeyboardById(midiKeyboardId);
            OwnerMidiKeyboardMap ownerMidiKeyboardMap = new OwnerMidiKeyboardMap();
            ownerMidiKeyboardMap.setOwner(createdOwner);
            ownerMidiKeyboardMap.setMidiKeyboard(midiKeyboard);
            ownerMidiKeyboardMapRepository.save(ownerMidiKeyboardMap);
        }
        //добавить связи между midiKeyboard и owner в таблицу midiKeyboardOwnerMap.
        OwnerDto returnData = new OwnerDto(createdOwner);
        returnData.setMidiKeyboardList(ownerKeyboard);
        return returnData;
    }

    public OwnerDto getOwnerById(Long ownerId) throws OwnerNotFoundException {
        return new OwnerDto(findOwnerById(ownerId));
    }

    public List<OwnerDto> getAllOwnerRecords() {
        Iterable<Owner> allRecords = ownerRepository.findAll();
        List<OwnerDto> ownerRecords = new ArrayList<>();
        for (Owner record : allRecords) {
            OwnerDto foundOwners = new OwnerDto(record);
            ownerRecords.add(foundOwners);
        }
        return ownerRecords;
    }

    public OwnerDto updateOwner(OwnerDto bodyOwner, Long ownerId) throws OwnerNotFoundException {
        Owner replaceOwner = findOwnerById(ownerId);
        replaceOwner.setName(bodyOwner.getName());
        replaceOwner.setSex(bodyOwner.getSex());
        replaceOwner.setAge(bodyOwner.getAge());
        replaceOwner.setSkillLevel(bodyOwner.getSkillLevel());
        Owner ownerUp = ownerRepository.save(replaceOwner);
        return new OwnerDto(ownerUp);
    }

    private Owner findOwnerById(Long ownerId) throws OwnerNotFoundException {
        Optional<Owner> ownerById = ownerRepository.findById(ownerId);
        if (ownerById.isPresent()) {
            return ownerById.get();
        }
        throw new OwnerNotFoundException(ownerId);
    }

    public List<OwnerDto> findByOwnerName(String ownerName) {
        List<OwnerDto> foundByOwner = new ArrayList<>();
        List<Owner> ownerList = ownerRepository.getByOwnerName(ownerName);
        for (Owner foundRecord : ownerList) {
            OwnerDto recordsByOwner = new OwnerDto(foundRecord);
            foundByOwner.add(recordsByOwner);
        }
        return foundByOwner;
    }

    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}


// midiKeyboardService зарефакторить методы getById и updateMidiKeyboard используя метод getMidiKeyboardById.
// добавить таблицу specification в которой будет содержаться техинформация о midikeyboard.
// прокинуть связь между таблицой specification и midiKeyboard oneToOne. дополнить все api в midikeyboardKontroller
// информацией из таблицы specification.