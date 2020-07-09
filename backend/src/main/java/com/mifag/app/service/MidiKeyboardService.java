package com.mifag.app.service;

import com.mifag.app.dto.MidiKeyboardDto;
import com.mifag.app.dto.SpecificationDto;
import com.mifag.app.entity.MidiKeyboard;
import com.mifag.app.entity.Specification;
import com.mifag.app.exception.MidiKeyboardNotFoundException;
import com.mifag.app.repository.MidiKeyboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Midi keyboard service .
 */
@Component
public class MidiKeyboardService {

    private final MidiKeyboardRepository midiKeyboardRepository;
    private final SpecificationService specificationService;


    @Autowired
    public MidiKeyboardService(MidiKeyboardRepository midiKeyboardRepository,
                               SpecificationService specificationService) {
        this.midiKeyboardRepository = midiKeyboardRepository;
        this.specificationService = specificationService;
    }

    /**
     * Create midi keyboard.
     * @param midiData - MidiKeyboardDto.
     * @return  new midi keyboard.
     */
    public MidiKeyboardDto createMidi(MidiKeyboardDto midiData) {
        try {
            return findByModel(midiData.getModel());
        } catch (MidiKeyboardNotFoundException e) {
            Boolean isSpecsPresent = false;
            if (midiData.getSpecification() != null) {
                isSpecsPresent = true;
            }
            MidiKeyboard midiToCreate = new MidiKeyboard(midiData);
            if (isSpecsPresent) {
                Specification specification = specificationService.saveSpecification(
                        new Specification(midiData.getSpecification()));
                midiToCreate.setSpecification(specification);
                midiToCreate.setSpecificationId(specification.getId());
            }
            MidiKeyboard createdKeyboard = midiKeyboardRepository.save(midiToCreate);
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(createdKeyboard);
            if (isSpecsPresent) {
                midiKeyboardDto.setSpecification(new SpecificationDto(createdKeyboard.getSpecification()));
            }
            return midiKeyboardDto;
        }
    }

    /**
     * Search midi keyboard by id.
     * @param midiIdToFindBy id.
     * @return found midi keyboard.
     * @throws MidiKeyboardNotFoundException if midi keyboard with specific id not found.
     */
    public MidiKeyboardDto getMidiById(Long midiIdToFindBy) throws MidiKeyboardNotFoundException {
        Specification specification = getMidiKeyboardById(midiIdToFindBy).getSpecification();
        MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(getMidiKeyboardById(midiIdToFindBy));
        if (specification != null) {
            midiKeyboardDto.setSpecification(new SpecificationDto(specification));
        }
        return midiKeyboardDto;
    }

    /**
     * Search all midi keyboards.
     * @return All keyboards.
     */
    public List<MidiKeyboardDto> getAllMidiRecords() {
        Iterable<MidiKeyboard> allRecords = midiKeyboardRepository.findAll();
        List<MidiKeyboardDto> midiRecords = new ArrayList<>();
        for (MidiKeyboard record : allRecords) {
            Long specId = record.getSpecificationId();
            MidiKeyboardDto midiDto = new MidiKeyboardDto(record);
            if (specId != null) {
                Specification specification = record.getSpecification();
                midiDto.setSpecification(new SpecificationDto(specification));
            }
            midiRecords.add(midiDto);
        }
        return midiRecords;
    }

    /**
     * Replace midi keyboard with specific id.
     * @param midiBoard - new midi keyboard.
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
        MidiKeyboard replacedMidiKeyboard = midiKeyboardRepository.save(midiKeyboard);
        return new MidiKeyboardDto(replacedMidiKeyboard);
    }

    /**
     * Delete midi keyboard with specific id.
     * @param deleteId - id.
     */
    public void deleteMidi(Long deleteId) {
        midiKeyboardRepository.deleteById(deleteId);
    }

    /**
     * Search midi keyboards by manufacturer name.
     * @param manufacturerName .
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByManufacturer(String manufacturerName) {
        List<MidiKeyboardDto> foundByManufacturer = new ArrayList<>();
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByManufacturerName(manufacturerName);
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByManufacturer = new MidiKeyboardDto(foundRecord);
            Long specId = foundRecord.getSpecificationId();
            if (specId != null) {
                recordsByManufacturer.setSpecification(new SpecificationDto(foundRecord.getSpecification()));
            }
            foundByManufacturer.add(recordsByManufacturer);
        }
        return foundByManufacturer;
    }

    /**
     * Search midi keyboards by number of key.
     * @param minKeys .
     * @param maxKeys .
     * @param equalsKeys .
     * @return Found midi keyboards.
     */
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

        /*
         * Условия выролнения поиска, если ни одно условие не выполняется - возвращается пустой лист.
         */
        if (Objects.nonNull(minKey) && Objects.nonNull(maxKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMinKeysAndMaxKeys(minKey, maxKey);
        } else if (Objects.nonNull(minKey) && Objects.nonNull(equalsKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMinKeysAndEqualsKeys(minKey, equalsKey);
        } else if (Objects.nonNull(maxKey) && Objects.nonNull(equalsKey)) {
            midiKeyboardList = midiKeyboardRepository.getByMaxKeysAndEqualsKeys(maxKey, equalsKey);
        } else {
           midiKeyboardList = new ArrayList<>();
        }

        /*
         * Если есть спецификация, в MidiKeyboardDto добавляется еще и она.
         */
        for (MidiKeyboard record : midiKeyboardList) {
            Long specId = record.getSpecificationId();
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(record);
            if (specId != null) {
                Specification specification = record.getSpecification();
                midiKeyboardDto.setSpecification(new SpecificationDto(specification));
            }
            foundByKeys.add(midiKeyboardDto);
        }
        return foundByKeys;
    }

    /**
     * Search midi keyboards by model.
     * @param model .
     * @return Found midi keyboards.
     * @throws MidiKeyboardNotFoundException  if midi keyboard with specific model not found.
     */
    public MidiKeyboardDto findByModel(String model) throws MidiKeyboardNotFoundException {
        MidiKeyboard foundRecord = midiKeyboardRepository.getByModel(model);
        if (foundRecord != null) {
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(foundRecord);
            Long specificationId = foundRecord.getSpecificationId();
            if (specificationId != null) {
                midiKeyboardDto.setSpecification(new SpecificationDto(foundRecord.getSpecification()));
            }
            return midiKeyboardDto;
        }
        throw new MidiKeyboardNotFoundException(model);
    }

    /**
     * Search midi keyboards by cost.
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
            Long specId = foundRecord.getSpecificationId();
            if (specId != null) {
                recordsByPrice.setSpecification(new SpecificationDto(foundRecord.getSpecification()));
            }
            foundByPrice.add(recordsByPrice);
        }
        return foundByPrice;
    }

    /**
     * Search midi keyboards by presence of midi out.
     * @param midiOut - the presence of midi output.
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByMidiOut(Boolean midiOut) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByMidiOut(midiOut);
        List<MidiKeyboardDto> foundByMidiOut = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByMidiOut = new MidiKeyboardDto(foundRecord);
            Long specId = foundRecord.getSpecificationId();
            if (specId != null) {
                recordsByMidiOut.setSpecification(new SpecificationDto(foundRecord.getSpecification()));
            }
            foundByMidiOut.add(recordsByMidiOut);
        }
        return foundByMidiOut;
    }

    /**
     * Search midi keyborads by part of manufacturer name.
     * @param part of manufacturer name.
     * @return Found midi keyboards.
     */
    public List<MidiKeyboardDto> findByPartOfManufacturer(String part) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByPartOfManufacturer(part);
        List<MidiKeyboardDto> foundByPartOfManufacturer = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByPartOfManufacturer = new MidiKeyboardDto(foundRecord);
            Long specsId = foundRecord.getSpecificationId();
            if (specsId != null) {
                recordsByPartOfManufacturer.setSpecification(new SpecificationDto(foundRecord.getSpecification()));
            }
            foundByPartOfManufacturer.add(recordsByPartOfManufacturer);
        }
        return foundByPartOfManufacturer;
    }

    /**
     * Search by id.
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
