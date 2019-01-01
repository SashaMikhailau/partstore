package com.azya.partstore.services;

import com.azya.partstore.models.Part;

import java.util.List;

public interface PartService {
    List<Part> getAllParts();
    List<Part> getAllParts(String searchQuery);

    void updatePart(Part part);

    void deletePart(Part part);

    long getAvailableComputersCount();

    PartViewMode getPartViewMode();

    void setPartViewMode(PartViewMode partViewMode);

}
