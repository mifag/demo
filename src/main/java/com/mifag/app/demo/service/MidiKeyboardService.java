package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.dto.SpecificationDto;
import com.mifag.app.demo.entity.MidiKeyboard;
import com.mifag.app.demo.entity.Specification;
import com.mifag.app.demo.exception.MidiKeyboardNotFoundException;
import com.mifag.app.demo.repository.MidiKeyboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис для обращения в репозиторий миди-клавиатур.
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
     * Создание новой мидиклавиатуры в базе данных.
     * @param midiData - параметры новой клавиатуры.
     * @return  данные из базы данных о новой миди-клавиатуре.
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
     * Поиск миди-клавиатуры по id.
     * @param midiIdToFindBy id миди-клавиатуры.
     * @return найденная мидиклавиатура в типе MidiKeyboardDto.
     * @throws MidiKeyboardNotFoundException отправляется в контроллер в случае отсутствия объекта с данным id.
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
     * Поиск всех миди-клавиатур в базе данных.
     * @return список найденных клавиатур.
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
     * Замена записи о миди-клавиатуре с запрошенным id в базе данных.
     * @param midiBoard - параметры новой миди-клавиатуры.
     * @param midiBoardId id заменяемой миди-клавиатуры.
     * @return данные новой миди-клавиатуры типа MidiKeyboardDto.
     * @throws MidiKeyboardNotFoundException отправляется в контроллер в случае отсутствия объекта с данным id.
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
     * Удаление миди-клавиатуры из базы данных по заданному id.
     * @param deleteId - id удаляемой клавиатуры.
     */
    public void deleteMidi(Long deleteId) {
        midiKeyboardRepository.deleteById(deleteId);
    }

    /**
     * Поиск в базе данных клавиатур по производителю.
     * @param manufacturerName .
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
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
     * Поиск в базе данных клавиатуры по количеству клавиш.
     * @param minKeys - минимальное количество клавиш.
     * @param maxKeys - максимальное количество клавиш.
     * @param equalsKeys - заданное количество клавиш.
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
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
     * Поиск в базе данных клавиатур по модели.
     * @param model .
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
     * @throws MidiKeyboardNotFoundException  отправляется в контроллер в случае отсутствия объекта данной модели.
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
     * Поиск в базе данных клавиатур по цене.
     * @param cost .
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
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
     * Поиск в базе данных клавиатур по наличию миди-выхода.
     * @param midiOut .
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
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
     * Поиск в базе данных клавиатур по части имени производителя.
     * @param part .
     * @return найденные мидиклавиатуры в типе MidiKeyboardDto.
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
     * Поиск в базе данных клавиатуры по id.
     * @param keyId .
     * @return найденная мидиклавиатура в базе данных.
     * @throws MidiKeyboardNotFoundException отправляется в контроллер в случае отсутствия объекта с данным id.
     */
    public MidiKeyboard getMidiKeyboardById(Long keyId) throws MidiKeyboardNotFoundException {
        Optional<MidiKeyboard> midiKeyboard = midiKeyboardRepository.findById(keyId);
        if (midiKeyboard.isPresent()) {
            return midiKeyboard.get();
        }
        throw new MidiKeyboardNotFoundException(keyId);
    }
}
