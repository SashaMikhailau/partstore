package com.azya.partstore.services;

import com.azya.partstore.PartRepository;
import com.azya.partstore.models.Part;
import com.azya.partstore.models.PartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {
    @Autowired
    private PartRepository partRepository;
    @Override
    public List<Part> getAllParts() {
        return (List<Part>)partRepository.findAll();
    }

    @Override
    public void updatePart(Part part) {

    }

    @Override
    public void deletePart(Part part) {

    }
    /*НУНЖО ИМЗЕНИТЬ КОЛЛЕКТОРС КАУНТИНГ НА СУММИРОВАНИЕ ЧИСЕЛ*/
    @Override
    public long getAvailableComputersCount() {
        List<Part> parts = getAllParts();
        Map<PartType, Long> typesCount = parts.stream()
                .filter(part->part.getType()
                        .isNeeded())
                .collect(Collectors.groupingBy(Part::getType, Collectors.counting()));

        Map<PartType, Long> partTypeMap = Arrays.stream(PartType.values())
                .filter(PartType::isNeeded)
                .collect(Collectors.toMap(partType -> partType, partType -> 0L));
        partTypeMap.putAll(typesCount);
        List<Long> counts = new ArrayList<>(partTypeMap.values());
        Collections.sort(counts);

        return counts.get(0);
    }
}
