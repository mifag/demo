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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public MidiKeyboardDto createMidi(MidiKeyboardDto midiData) {
        try {
            return findByModel(midiData.getModel());
        } catch (MidiKeyboardNotFoundException e) {
            Boolean isSpecsPresent = false;
            if (midiData.getSpecification() != null) {
                isSpecsPresent = true;
            }
            MidiKeyboard midiToCreate = new MidiKeyboard(midiData);

            //midiToCreate.setManufacturer(midiData.getManufacturer());
            //midiToCreate.setModel(midiData.getModel());
            //midiToCreate.setKeysNumber(midiData.getKeysNumber());
            //midiToCreate.setHasMidiOut(midiData.getHasMidiOut());
            //midiToCreate.setPrice(midiData.getPrice());

            //извлечь SpecificationDto из midiData
            //передать данные из SpecificationDto в Specification
            //установить данные из Specification в Midikeyboard
            //сохранить данные в репозиторий и вернуть их
            //записть SpecificationDto в midiDto и вернуть в метод createMidi
            if (isSpecsPresent) {
                //SpecificationDto specDto = midiData.getSpecification();
                //Specification spec = new Specification(specDto);

                //spec.setWeight(specDto.getWeight());
                //spec.setLength(specDto.getLength());
                //spec.setWidth(specDto.getWidth());
                //spec.setVelocity(specDto.getVelocity());
                //spec.setTypeOfKey(specDto.getTypeOfKey());

                Specification specification = specificationService.saveSpecification(
                        new Specification(midiData.getSpecification()));
                midiToCreate.setSpecification(specification);
                midiToCreate.setSpecificationId(specification.getId());
            }
            //midiToCreate.setSpecification
            MidiKeyboard createdKeyboard = midiKeyboardRepository.save(midiToCreate);
            MidiKeyboardDto midiKeyboardDto = new MidiKeyboardDto(createdKeyboard);

            //.getId(),
             //       createdKeyboard.getManufacturer(), createdKeyboard.getModel(), createdKeyboard.getKeysNumber(),
              //      createdKeyboard.getHasMidiOut(), createdKeyboard.getPrice()
            //вытащить specification из createdKeyboard
            //передать из specification B specificationDto
            //вернуть в keyboardDto
            if (isSpecsPresent) {
                //Specification specification = createdKeyboard.getSpecification();
                //SpecificationDto specificationDto = new SpecificationDto(specification);
                //specificationDto.setId(specification.getId());
                //specificationDto.setWeight(specification.getWeight());
                //specificationDto.setLength(specification.getLength());
                //specificationDto.setWidth(specification.getWidth());
                //specificationDto.setVelocity(specification.getVelocity());
                //specificationDto.setTypeOfKey(specification.getTypeOfKey());
                midiKeyboardDto.setSpecification(new SpecificationDto(createdKeyboard.getSpecification()));
            }
            return midiKeyboardDto;
        }
        //найти midiKeyboard по модели.
        //если её нет, то создать новую.
        //если она есть, то
        //проверить, есть ли записи с такой model,если есть,вернуть существующую.
        //если нет, создать новую и вернуть созданную.

    }

    public MidiKeyboardDto getMidiById(Long midiIdToFindBy) throws MidiKeyboardNotFoundException {
        return new MidiKeyboardDto(getMidiKeyboardById(midiIdToFindBy));
    }

    public List<MidiKeyboardDto> getAllMidiRecords() {
        Iterable<MidiKeyboard> allRecords = midiKeyboardRepository.findAll();
        List<MidiKeyboardDto> midiRecords = new ArrayList<>();
        for (MidiKeyboard record : allRecords) {
            MidiKeyboardDto midiDto = new MidiKeyboardDto(record);
            //.getId(), record.getManufacturer(), record.getModel(),
            //        record.getKeysNumber(), record.getHasMidiOut(), record.getPrice()
            midiRecords.add(midiDto);
        }
        return midiRecords;
    }

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

   // Optional<MidiKeyboard> replaceMidiBoard = midiKeyboardRepository.findById(midiBoardId);
     //   if (replaceMidiBoard.isPresent()) {
       // MidiKeyboard boardToUpdate = replaceMidiBoard.get();
      //  boardToUpdate.setManufacturer(midiBoard.getManufacturer());
      //  boardToUpdate.setModel(midiBoard.getModel());
      //  boardToUpdate.setKeysNumber(midiBoard.getKeysNumber());
      //  boardToUpdate.setHasMidiOut(midiBoard.getHasMidiOut());
      //  boardToUpdate.setPrice(midiBoard.getPrice());
       // MidiKeyboard key = midiKeyboardRepository.save(boardToUpdate);
     //  return new MidiKeyboardDto(key);
   // }
     //   return null;

    public void deleteMidi(Long deleteId) {
        midiKeyboardRepository.deleteById(deleteId);
    }

    public List<MidiKeyboardDto> findByManufacturer(String manufacturerName) {
        List<MidiKeyboardDto> foundByManufacturer = new ArrayList<>();
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByManufacturerName(manufacturerName);
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByManufacturer = new MidiKeyboardDto(foundRecord);
        //    .getId(),
        //            foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
        //            foundRecord.getHasMidiOut(), foundRecord.getPrice()
            foundByManufacturer.add(recordsByManufacturer);
        }
        return foundByManufacturer;
    }

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
           // MidiKeyboardDto midiDto = new MidiKeyboardDto(record.getId(), record.getManufacturer(),
           //         record.getModel(), record.getKeysNumber(), record.getHasMidiOut(), record.getPrice());
            foundByKeys.add(new MidiKeyboardDto(record));
        }
        return foundByKeys;
    }

    public MidiKeyboardDto findByModel(String model) throws MidiKeyboardNotFoundException {
        MidiKeyboard foundRecord = midiKeyboardRepository.getByModel(model);
        if (foundRecord != null) {
            return new MidiKeyboardDto(foundRecord);
           // .getId(),
           //         foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
           //         foundRecord.getHasMidiOut(), foundRecord.getPrice()
        }
        throw new MidiKeyboardNotFoundException(model);
    }

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
         //   .getId(),
         //           foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
         //           foundRecord.getHasMidiOut(), foundRecord.getPrice()
            foundByPrice.add(recordsByPrice);
        }
        return foundByPrice;
    }

    public List<MidiKeyboardDto> findByMidiOut(Boolean midiOut) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByMidiOut(midiOut);
        List<MidiKeyboardDto> foundByMidiOut = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByMidiOut = new MidiKeyboardDto(foundRecord);
        //    .getId(),
        //            foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
        //            foundRecord.getHasMidiOut(), foundRecord.getPrice()
            foundByMidiOut.add(recordsByMidiOut);
        }
        return foundByMidiOut;
    }

    public List<MidiKeyboardDto> findByPartOfManufacturer(String part) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByPartOfManufacturer(part);
        List<MidiKeyboardDto> foundByPartOfManufacturer = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByPartOfManufacturer = new MidiKeyboardDto(foundRecord);
         //   .getId(),
         //           foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
         //           foundRecord.getHasMidiOut(), foundRecord.getPrice()
            foundByPartOfManufacturer.add(recordsByPartOfManufacturer);
        }
        return foundByPartOfManufacturer;
    }

    public MidiKeyboard getMidiKeyboardById(Long keyId) throws MidiKeyboardNotFoundException {
        Optional<MidiKeyboard> midiKeyboard = midiKeyboardRepository.findById(keyId);
        if (midiKeyboard.isPresent()) {
            return midiKeyboard.get();
        }
        throw new MidiKeyboardNotFoundException(keyId);
    }
}
