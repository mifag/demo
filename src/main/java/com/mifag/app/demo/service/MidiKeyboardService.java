package com.mifag.app.demo.service;

import com.mifag.app.demo.dto.MidiKeyboardDto;
import com.mifag.app.demo.entity.MidiKeyboard;
import com.mifag.app.demo.repository.MidiKeyboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MidiKeyboardService {

    private final MidiKeyboardRepository midiKeyboardRepository;

    @Autowired
    public MidiKeyboardService(MidiKeyboardRepository midiKeyboardRepository) {
        this.midiKeyboardRepository = midiKeyboardRepository;
    }

    public MidiKeyboardDto createMidi(MidiKeyboardDto midiData) {
        MidiKeyboard midiToCreate = new MidiKeyboard();
        midiToCreate.setManufacturer(midiData.getManufacturer());
        midiToCreate.setModel(midiData.getModel());
        midiToCreate.setKeysNumber(midiData.getKeysNumber());
        midiToCreate.setHasMidiOut(midiData.getHasMidiOut());
        midiToCreate.setPrice(midiData.getPrice());

        MidiKeyboard createdKeyboard = midiKeyboardRepository.save(midiToCreate);

        return new MidiKeyboardDto(createdKeyboard.getId(),
                createdKeyboard.getManufacturer(), createdKeyboard.getModel(), createdKeyboard.getKeysNumber(),
                createdKeyboard.getHasMidiOut(), createdKeyboard.getPrice());
    }

    public MidiKeyboardDto getMidiById(Long midiIdToFindBy) {
        Optional<MidiKeyboard> foundMidi = midiKeyboardRepository.findById(midiIdToFindBy);
        MidiKeyboardDto midiData = new MidiKeyboardDto();
        if (foundMidi.isPresent()) {
            MidiKeyboard keyboard = foundMidi.get();
            midiData = new MidiKeyboardDto(keyboard.getId(), keyboard.getManufacturer(), keyboard.getModel(),
                    keyboard.getKeysNumber(), keyboard.getHasMidiOut(), keyboard.getPrice());
        }
        return midiData;
    }

    public List<MidiKeyboardDto> getAllMidiRecords() {
        Iterable<MidiKeyboard> allRecords = midiKeyboardRepository.findAll();
        List<MidiKeyboardDto> midiRecords = new ArrayList<>();
        for (MidiKeyboard record : allRecords) {
            MidiKeyboardDto midiDto = new MidiKeyboardDto(record.getId(), record.getManufacturer(), record.getModel(),
            record.getKeysNumber(), record.getHasMidiOut(), record.getPrice());
            midiRecords.add(midiDto);
        }
        return midiRecords;
    }

    public MidiKeyboardDto updateMidiKeyboard(MidiKeyboardDto midiBoard, Long midiBoardId) {
        Optional<MidiKeyboard> replaceMidiBoard = midiKeyboardRepository.findById(midiBoardId);
        if (replaceMidiBoard.isPresent()) {
            MidiKeyboard boardToUpdate = replaceMidiBoard.get();
            boardToUpdate.setManufacturer(midiBoard.getManufacturer());
            boardToUpdate.setModel(midiBoard.getModel());
            boardToUpdate.setKeysNumber(midiBoard.getKeysNumber());
            boardToUpdate.setHasMidiOut(midiBoard.getHasMidiOut());
            boardToUpdate.setPrice(midiBoard.getPrice());
            MidiKeyboard key = midiKeyboardRepository.save(boardToUpdate);
            return new MidiKeyboardDto(key.getId(), key.getManufacturer(), key.getModel(),
                    key.getKeysNumber(), key.getHasMidiOut(), key.getPrice());
        }
        return null;
    }

    public void deleteMidi(Long deleteId) {
        midiKeyboardRepository.deleteById(deleteId);
    }

    public List<MidiKeyboardDto> findByManufacturer(String manufacturerName) {
        List<MidiKeyboardDto> foundByManufacturer = new ArrayList<>();
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByManufacturerName(manufacturerName);
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByManufacturer = new MidiKeyboardDto(foundRecord.getId(),
                    foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
                    foundRecord.getHasMidiOut(), foundRecord.getPrice());
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
            MidiKeyboardDto midiDto = new MidiKeyboardDto(record.getId(), record.getManufacturer(),
                    record.getModel(), record.getKeysNumber(), record.getHasMidiOut(), record.getPrice());
            foundByKeys.add(midiDto);
        }
        return foundByKeys;
    }

    public List<MidiKeyboardDto> findByModel(String model) {
        List<MidiKeyboardDto> foundByModel = new ArrayList<>();
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByModel(model);
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByModel = new MidiKeyboardDto(foundRecord.getId(),
                    foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
                    foundRecord.getHasMidiOut(), foundRecord.getPrice());
            foundByModel.add(recordsByModel);
        }
        return foundByModel;
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
            MidiKeyboardDto recordsByPrice = new MidiKeyboardDto(foundRecord.getId(),
                    foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
                    foundRecord.getHasMidiOut(), foundRecord.getPrice());
            foundByPrice.add(recordsByPrice);
        }
        return foundByPrice;
    }

    public List<MidiKeyboardDto> findByMidiOut(Boolean midiOut) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByMidiOut(midiOut);
        List<MidiKeyboardDto> foundByMidiOut = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByMidiOut = new MidiKeyboardDto(foundRecord.getId(),
                    foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
                    foundRecord.getHasMidiOut(), foundRecord.getPrice());
            foundByMidiOut.add(recordsByMidiOut);
        }
        return foundByMidiOut;
    }

    public List<MidiKeyboardDto> findByPartOfManufacturer(String part) {
        List<MidiKeyboard> midiKeyboardList = midiKeyboardRepository.getByPartOfManufacturer(part);
        List<MidiKeyboardDto> foundByPartOfManufacturer = new ArrayList<>();
        for (MidiKeyboard foundRecord : midiKeyboardList) {
            MidiKeyboardDto recordsByPartOfManufacturer = new MidiKeyboardDto(foundRecord.getId(),
                    foundRecord.getManufacturer(), foundRecord.getModel(), foundRecord.getKeysNumber(),
                    foundRecord.getHasMidiOut(), foundRecord.getPrice());
            foundByPartOfManufacturer.add(recordsByPartOfManufacturer);
        }
        return foundByPartOfManufacturer;
    }
}
