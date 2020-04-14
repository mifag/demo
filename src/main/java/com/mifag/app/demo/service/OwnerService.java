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
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для обращения в репозиторий владельцев.
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
     * Создание нового владельца в базе данных.
     * @param ownerData - данные нового владельца.
     * @return созданный владелец.
     * @throws MidiKeyboardNotFoundException отправляется на клиент в случае отсутствия данных миди-клавитуры.
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
     * Поиск владельца по id.
     * @param ownerId - запрошенный id.
     * @return данные владельца в типе OwnerDto с запрошенным id.
     * @throws OwnerNotFoundException отправляется в контроллер в случае отсутствия владельца по данному id.
     */
    public OwnerDto getOwnerById(Long ownerId) throws OwnerNotFoundException {
        Owner owner = findOwnerById(ownerId);
        OwnerDto ownerDto = new OwnerDto(owner);
        List<MidiKeyboardDto> midiKeyboardDtoList = new ArrayList<>();
        for (OwnerMidiKeyboardMap ownerMidiKeyboardMap : owner.getOwnerMidiKeyboardMaps()) {
            MidiKeyboard midiKeyboard = ownerMidiKeyboardMap.getMidiKeyboard();
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(midiKeyboard);
            midiKeyboardDtoList.add(midiKeyboardDto);
        }
        ownerDto.setMidiKeyboardList(midiKeyboardDtoList);
        return ownerDto;
    }

    /**
     * Поиск всех владельцев в базе данных.
     * @return список найденных владельцев.
     */
    public List<OwnerDto> getAllOwnerRecords() {
        Iterable<Owner> allRecords = ownerRepository.findAll();
        List<OwnerDto> ownerRecords = new ArrayList<>();
        for (Owner record : allRecords) {
            List<MidiKeyboardDto> midiKeyboardDtos = new ArrayList<>();
            for (OwnerMidiKeyboardMap ownerMidiKeyboardMap : record.getOwnerMidiKeyboardMaps()){
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
     * Замена записи в базе данных о владельце с запрошенным id.
     * @param bodyOwner - данные нового владельца.
     * @param ownerId -id заменяемого владельца.
     * @return данные из базы данных нового владельца.
     * @throws OwnerNotFoundException отправляется в контроллер в случае отсутствия владельца с данным id.
     * @throws MidiKeyboardNotFoundException отправляется в контроллер в случае отсутствия данных миди-клавиатуры.
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
     * Поиск в базе данных владельца по id.
     * @param ownerId - id владельца.
     * @return entity найденного владельца.
     * @throws OwnerNotFoundException отправляется в контроллер в случае отсутствия владельца с данным id.
     */
    private Owner findOwnerById(Long ownerId) throws OwnerNotFoundException {
        Optional<Owner> ownerById = ownerRepository.findById(ownerId);
        if (ownerById.isPresent()) {
            return ownerById.get();
        }
        throw new OwnerNotFoundException(ownerId);
    }

    /**
     * Поиск в базе данных владельцев по имени.
     * @param ownerName - имя владельца.
     * @return список найденных владельцев.
     */
    public List<OwnerDto> findByOwnerName(String ownerName) {
        List<OwnerDto> foundByOwner = new ArrayList<>();
        List<Owner> ownerList = ownerRepository.getByOwnerName(ownerName);
        for (Owner foundRecord : ownerList) {
            List<MidiKeyboardDto> midiKeyboardDtoList= new ArrayList<>();
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
     * Удаление владельца из базы данных.
     * @param ownerId - id удаляемого владельца.
     */
    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}
