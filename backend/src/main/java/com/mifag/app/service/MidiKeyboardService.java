package com.mifag.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

import com.mifag.app.dto.MidiKeyboardDto;
import com.mifag.app.dto.OwnerDto;
import com.mifag.app.entity.MidiKeyboard;
import com.mifag.app.entity.OwnerMidiKeyboardMap;
import com.mifag.app.entity.specification.MidiKeyboardSpecification;
import com.mifag.app.exception.MidiKeyboardNotFoundException;
import com.mifag.app.repository.MidiKeyboardRepository;

/**
 * Midi keyboard service .
 */
@Component
public class MidiKeyboardService {

    private final MidiKeyboardRepository midiKeyboardRepository;

    /**
     * Constructor.
     *
     * @param midiKeyboardRepository - repository of midi-keyboards.
     */
    @Autowired
    public MidiKeyboardService(MidiKeyboardRepository midiKeyboardRepository) {
        this.midiKeyboardRepository = midiKeyboardRepository;
    }

    /**
     * search by parameters.
     *
     * @param midiKeyboardDto - dto with parameters.
     * @return list of midi-keyboard.
     */
    public List<MidiKeyboardDto> search(MidiKeyboardDto midiKeyboardDto) {
        Specification<MidiKeyboard> specification = MidiKeyboardSpecification.findAll();
        if (Objects.nonNull(midiKeyboardDto.getId())) {
            specification = where(specification).and(MidiKeyboardSpecification.findById(midiKeyboardDto.getId()));
        }
        if (StringUtils.isNoneEmpty(midiKeyboardDto.getManufacturer())) {
            specification = where(specification).and(MidiKeyboardSpecification.findByManufacturer(
                    midiKeyboardDto.getManufacturer()));
        }
        if (StringUtils.isNoneEmpty(midiKeyboardDto.getModel())) {
            specification = where(specification).and(MidiKeyboardSpecification.findByModel(
                    midiKeyboardDto.getModel()));
        }
        if (Objects.nonNull(midiKeyboardDto.getKeysNumber())) {
            specification = where(specification).and(MidiKeyboardSpecification.findByKeys(
                    midiKeyboardDto.getKeysNumber()));
        }
        if (Objects.nonNull(midiKeyboardDto.getHasMidiOut())) {
            specification = where(specification).and(MidiKeyboardSpecification.findByMidiOut(
                    midiKeyboardDto.getHasMidiOut()));
        }
        if (Objects.nonNull(midiKeyboardDto.getPrice())) {
            specification = where(specification).and(MidiKeyboardSpecification.findByPrice(
                    midiKeyboardDto.getPrice()));
        }
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.findAll(specification);
        List<MidiKeyboardDto> midiKeyboardDtoList = new ArrayList<>();
        for (MidiKeyboard midiKeyboard : midiKeyboardList) {
            midiKeyboardDtoList.add(new MidiKeyboardDto(midiKeyboard));
        }
        midiKeyboardDtoList.sort(Comparator.comparing(MidiKeyboardDto::getId));
        return midiKeyboardDtoList;
    }

    /**
     * Create midi keyboard.
     *
     * @param midiData - MidiKeyboardDto.
     * @return new midi keyboard.
     */
    public MidiKeyboardDto createMidi(MidiKeyboardDto midiData) {
        try {
            return findByModel(midiData.getModel());
        } catch (MidiKeyboardNotFoundException e) {
            MidiKeyboard midiToCreate = new MidiKeyboard(midiData);
            MidiKeyboard createdKeyboard = midiKeyboardRepository.save(midiToCreate);
            return new MidiKeyboardDto(createdKeyboard);
        }
    }

    /**
     * Search midi keyboard by id.
     *
     * @param midiId id.
     * @return found midi keyboard.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    public MidiKeyboardDto getMidiById(Long midiId) throws MidiKeyboardNotFoundException {
        Optional<MidiKeyboard> optionalMidiKeyboard = midiKeyboardRepository.findById(midiId);
        if (optionalMidiKeyboard.isEmpty()) {
            throw new MidiKeyboardNotFoundException(midiId);
        }
        MidiKeyboard midiKeyboard = optionalMidiKeyboard.get();
        MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(midiKeyboard);
        List<OwnerDto> ownerDtoList = new ArrayList<>();
        for (OwnerMidiKeyboardMap ownerMidiKeyboardMap : midiKeyboard.getOwnerMidiKeyboardMaps()) {
            ownerDtoList.add(new OwnerDto(ownerMidiKeyboardMap.getOwner()));
        }
        midiKeyboardDto.setOwners(ownerDtoList);
        return midiKeyboardDto;
    }

    /**
     * Search all midi keyboards.
     *
     * @return All keyboards.
     */
    public List<MidiKeyboardDto> getAllMidiRecords() {
        Iterable<MidiKeyboard> allRecords = midiKeyboardRepository.findAll();
        List<MidiKeyboardDto> midiRecords = new ArrayList<>();
        for (MidiKeyboard record : allRecords) {
            MidiKeyboardDto midiDto = new MidiKeyboardDto(record);
            midiRecords.add(midiDto);
        }
        return midiRecords;
    }

    /**
     * Replace midi keyboard with specific id.
     *
     * @param midiBoard   - new midi keyboard.
     * @param midiBoardId id .
     * @return new MidiKeyboardDto.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    public MidiKeyboardDto updateMidiKeyboard(MidiKeyboardDto midiBoard, Long midiBoardId)
            throws MidiKeyboardNotFoundException {
        MidiKeyboard midiKeyboard = getMidiKeyboardById(midiBoardId);
        midiKeyboard.setManufacturer(midiBoard.getManufacturer());
        midiKeyboard.setModel(midiBoard.getModel());
        midiKeyboard.setKeysNumber(midiBoard.getKeysNumber());
        midiKeyboard.setHasMidiOut(midiBoard.getHasMidiOut());
        midiKeyboard.setPrice(midiBoard.getPrice());
        midiKeyboard.setSpecificationId(midiBoard.getSpecificationId());
        MidiKeyboard replacedMidiKeyboard = midiKeyboardRepository.save(midiKeyboard);
        return new MidiKeyboardDto(replacedMidiKeyboard);
    }

    /**
     * Delete midi keyboard with specific id.
     *
     * @param deleteId - id.
     */
    public void deleteMidi(Long deleteId) {
        midiKeyboardRepository.deleteById(deleteId);
    }

    /**
     * Search midi keyboards by manufacturer name.
     *
     * @param manufacturerName .
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByManufacturer(String manufacturerName) {
        List<MidiKeyboardDto> foundByManufacturer = new ArrayList<>();
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByManufacturerName(manufacturerName);
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByManufacturer = new MidiKeyboardDto(foundRecord);
            foundByManufacturer.add(recordsByManufacturer);
        }
        return foundByManufacturer;
    }

    /**
     * Search midi keyboards by number of key.
     *
     * @param minKeys    .
     * @param maxKeys    .
     * @param equalsKeys .
     * @return Found midi keyboards.
     */
    @SuppressWarnings("checkstyle:LineLength")
    public List<MidiKeyboardDto> findByKeys(Long minKeys, Long maxKeys, Long equalsKeys) {
        Integer minKey;
        if (minKeys != null) {
            minKey = minKeys.intValue();
        } else {
            minKey = null;
        }
        Integer maxKey;
        try {
            maxKey = maxKeys.intValue();
        } catch (NullPointerException nullPointerException) {
            maxKey = null;
        }
        Integer equalsKey = null;
        if (Objects.nonNull(equalsKeys)) {
            equalsKey = equalsKeys.intValue();
        }

        List<MidiKeyboard> midiKeyboardList;
        List<MidiKeyboardDto> foundByKeys = new ArrayList<>();

        if (Objects.nonNull(minKey) && Objects.nonNull(maxKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMinKeysAndMaxKeys(minKey, maxKey);
        } else if (Objects.nonNull(minKey) && Objects.nonNull(equalsKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMinKeysAndEqualsKeys(minKey, equalsKey);
        } else if (Objects.nonNull(maxKey) && Objects.nonNull(equalsKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMaxKeysAndEqualsKeys(maxKey, equalsKey);
        } else {
            midiKeyboardList = new ArrayList<>();
        }

        for (MidiKeyboard record : midiKeyboardList) {
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(record);
            foundByKeys.add(midiKeyboardDto);
        }
        return foundByKeys;
    }

    /**
     * Search midi keyboards by model.
     *
     * @param model .
     * @return Found midi keyboards.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific model not found.
     */
    public MidiKeyboardDto findByModel(String model) throws MidiKeyboardNotFoundException {
        MidiKeyboard foundRecord = midiKeyboardRepository.getByModel(model);
        if (foundRecord != null) {
            return new MidiKeyboardDto(foundRecord);
        }
        throw new MidiKeyboardNotFoundException(model);
    }

    /**
     * Search midi keyboards by cost.
     *
     * @param cost .
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByPrice(Integer cost) {
        Long costKey;
        try {
            costKey = cost.longValue();
        } catch (NullPointerException nullPointerException) {
            costKey = null;
        }

        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByPrice(costKey);
        List<MidiKeyboardDto> foundByPrice = new ArrayList<>();

        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByPrice = new MidiKeyboardDto(foundRecord);
            foundByPrice.add(recordsByPrice);
        }
        return foundByPrice;
    }

    /**
     * Search midi keyboards by presence of midi out.
     *
     * @param midiOut - the presence of midi output.
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByMidiOut(Boolean midiOut) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByMidiOut(midiOut);
        List<MidiKeyboardDto> foundByMidiOut = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByMidiOut = new MidiKeyboardDto(foundRecord);
            foundByMidiOut.add(recordsByMidiOut);
        }
        return foundByMidiOut;
    }

    /**
     * Search midi keyborads by part of manufacturer name.
     *
     * @param part of manufacturer name.
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByPartOfManufacturer(String part) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByPartOfManufacturer(part);
        List<MidiKeyboardDto> foundByPartOfManufacturer = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByPartOfManufacturer = new MidiKeyboardDto(foundRecord);
            foundByPartOfManufacturer.add(recordsByPartOfManufacturer);
        }
        return foundByPartOfManufacturer;
    }

    /**
     * Search by id.
     *
     * @param keyId - id.
     * @return found midi keyboard.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    public MidiKeyboard getMidiKeyboardById(Long keyId) throws MidiKeyboardNotFoundException {
        Optional<MidiKeyboard> midiKeyboard = midiKeyboardRepository.findById(keyId);
        if (midiKeyboard.isPresent()) {
            return midiKeyboard.get();
        }
        throw new MidiKeyboardNotFoundException(keyId);
    }
}
