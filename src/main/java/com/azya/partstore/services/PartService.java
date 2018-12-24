package com.azya.partstore.services;

import com.azya.partstore.models.Part;

import java.util.List;

public interface PartService {
    List<Part> getAllParts();

    void updatePart(Part part);

    void deletePart(Part part);

    int getAvailableComputersCount();

}
