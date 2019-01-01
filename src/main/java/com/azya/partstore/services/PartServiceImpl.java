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
    private PartViewMode partViewMode = PartViewMode.ALL;
    @Override
    public List<Part> getAllParts() {
        return (List<Part>)partRepository.findAll();
    }

    @Override
    public List<Part> getAllParts(String searchQuery) {
        Map<Boolean, List<Part>> partMap = getAllParts().stream()
                .filter(part -> part.getName().contains(searchQuery))
                .collect(Collectors.partitioningBy(part -> part.getType().isNeeded()));
        switch (partViewMode) {
            case NEEDED:
                return partMap.get(true);
            case NOTNEEDED:
                return partMap.get(false);
                default:return partMap.values().stream().flatMap(Collection::stream).sorted().collect(Collectors.toList());
        }
    }

    @Override
    public PartViewMode getPartViewMode() {
        return partViewMode;
    }

    public void setPartViewMode(PartViewMode partViewMode) {
        this.partViewMode = partViewMode;
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
