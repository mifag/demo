package com.mifag.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mifag.app.dto.MidiKeyboardDto;
import com.mifag.app.dto.OwnerDto;
import com.mifag.app.entity.MidiKeyboard;
import com.mifag.app.entity.Owner;
import com.mifag.app.entity.OwnerMidiKeyboardMap;
import com.mifag.app.exception.MidiKeyboardNotFoundException;
import com.mifag.app.exception.OwnerNotFoundException;
import com.mifag.app.repository.OwnerMidiKeyboardMapRepository;
import com.mifag.app.repository.OwnerRepository;

/**
 * Owner service.
 */
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

    /**
     * Create owner.
     *
     * @param ownerData - new owner.
     * @return created owner.
     * @throws MidiKeyboardNotFoundException if that owner hasn't midi keyboard.
     */
    public OwnerDto createOwner(OwnerDto ownerData) throws MidiKeyboardNotFoundException {
        Owner ownerToCreate = new Owner(ownerData);
        Owner createdOwner = ownerRepository.save(ownerToCreate);
        List<MidiKeyboardDto> midiKeyboardDtoList = ownerData.getMidiKeyboardList();
        List<MidiKeyboardDto> ownerKeyboard = new ArrayList<>();
        for (MidiKeyboardDto midiKeyboardDto : midiKeyboardDtoList) {
            MidiKeyboardDto idDto = midiKeyboardService.createMidi(midiKeyboardDto);
            ownerKeyboard.add(idDto);
            Long midiKeyboardId = idDto.getId();
            MidiKeyboard midiKeyboard = midiKeyboardService.getMidiKeyboardById(midiKeyboardId);
            OwnerMidiKeyboardMap ownerMidiKeyboardMap = new OwnerMidiKeyboardMap();
            ownerMidiKeyboardMap.setOwner(createdOwner);
            ownerMidiKeyboardMap.setMidiKeyboard(midiKeyboard);
            ownerMidiKeyboardMapRepository.save(ownerMidiKeyboardMap);
        }
        OwnerDto returnData = new OwnerDto(createdOwner);
        returnData.setMidiKeyboardList(ownerKeyboard);
        return returnData;
    }

    /**
     * Search owner by id.
     *
     * @param ownerId .
     * @return found owner.
     * @throws OwnerNotFoundException if owner with specific id not found.
     */
    public OwnerDto getOwnerById(Long ownerId) throws OwnerNotFoundException {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isEmpty()) {
            throw new OwnerNotFoundException(ownerId);
        }
        Owner owner = optionalOwner.get();
        OwnerDto ownerDto = new OwnerDto(owner);
        List<MidiKeyboardDto> midiKeyboardDtoList = new ArrayList<>();
        for (OwnerMidiKeyboardMap ownerMidiKeyboardMap : owner.getOwnerMidiKeyboardMaps()) {
            MidiKeyboard midiKeyboard = ownerMidiKeyboardMap.getMidiKeyboard();
            midiKeyboardDtoList.add(new MidiKeyboardDto(midiKeyboard));
        }
        ownerDto.setMidiKeyboardList(midiKeyboardDtoList);
        return ownerDto;
    }

    /**
     * Search all owners.
     *
     * @return all owners.
     */
    public List<OwnerDto> getAllOwnerRecords() {
        Iterable<Owner> allRecords = ownerRepository.findAll();
        List<OwnerDto> ownerRecords = new ArrayList<>();
        for (Owner record : allRecords) {
            List<MidiKeyboardDto> midiKeyboardDtos = new ArrayList<>();
            for (OwnerMidiKeyboardMap ownerMidiKeyboardMap : record.getOwnerMidiKeyboardMaps()) {
                MidiKeyboard midiKeyboard = ownerMidiKeyboardMap.getMidiKeyboard();
                MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(midiKeyboard);
                midiKeyboardDtos.add(midiKeyboardDto);
            }
            OwnerDto foundOwners = new OwnerDto(record);
            foundOwners.setMidiKeyboardList(midiKeyboardDtos);
            ownerRecords.add(foundOwners);
        }
        return ownerRecords;
    }

    /**
     * Replace owner with specific id.
     *
     * @param bodyOwner - new owner.
     * @param ownerId   - old owner id.
     * @return new owner.
     * @throws OwnerNotFoundException        if owner with specific id not found.
     * @throws MidiKeyboardNotFoundException if that owner hasn't midi keyboard.
     */
    public OwnerDto updateOwner(OwnerDto bodyOwner, Long ownerId) throws OwnerNotFoundException,
            MidiKeyboardNotFoundException {
        Owner replaceOwner = findOwnerById(ownerId);
        replaceOwner.setName(bodyOwner.getName());
        replaceOwner.setSex(bodyOwner.getSex());
        replaceOwner.setAge(bodyOwner.getAge());
        replaceOwner.setSkillLevel(bodyOwner.getSkillLevel());
        Owner ownerUp = ownerRepository.save(replaceOwner);
        List<MidiKeyboardDto> midiKeyboardDtos = bodyOwner.getMidiKeyboardList();
        List<MidiKeyboardDto> midiKeyboardDtoList = new ArrayList<>();
        for (MidiKeyboardDto midiKeyboardDto : midiKeyboardDtos) {
            Long midiId = midiKeyboardDto.getId();
            MidiKeyboardDto midiKeyboardDto1 = midiKeyboardService.updateMidiKeyboard(midiKeyboardDto, midiId);
            midiKeyboardDtoList.add(midiKeyboardDto1);
        }
        OwnerDto ownerDto = new OwnerDto(ownerUp);
        ownerDto.setMidiKeyboardList(midiKeyboardDtoList);
        return ownerDto;
    }

    /**
     * Search owner with specific id.
     *
     * @param ownerId - id.
     * @return found owner.
     * @throws OwnerNotFoundException if owner with specific id not found.
     */
    private Owner findOwnerById(Long ownerId) throws OwnerNotFoundException {
        Optional<Owner> ownerById = ownerRepository.findById(ownerId);
        if (ownerById.isPresent()) {
            return ownerById.get();
        }
        throw new OwnerNotFoundException(ownerId);
    }

    /**
     * Search owner by name.
     *
     * @param ownerName .
     * @return found owners.
     */
    public List<OwnerDto> findByOwnerName(String ownerName) {
        List<OwnerDto> foundByOwner = new ArrayList<>();
        List<Owner> ownerList = ownerRepository.getByOwnerName(ownerName);
        for (Owner foundRecord : ownerList) {
            List<MidiKeyboardDto> midiKeyboardDtoList = new ArrayList<>();
            for (OwnerMidiKeyboardMap keyboardMap : foundRecord.getOwnerMidiKeyboardMaps()) {
                MidiKeyboard midiKeyboard = keyboardMap.getMidiKeyboard();
                MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(midiKeyboard);
                midiKeyboardDtoList.add(midiKeyboardDto);
            }
            OwnerDto recordsByOwner = new OwnerDto(foundRecord);
            recordsByOwner.setMidiKeyboardList(midiKeyboardDtoList);
            foundByOwner.add(recordsByOwner);
        }
        return foundByOwner;
    }

    /**
     * Delete owner with specific id.
     *
     * @param ownerId .
     */
    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}
