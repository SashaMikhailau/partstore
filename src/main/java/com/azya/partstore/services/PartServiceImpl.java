package com.azya.partstore.services;

import com.azya.partstore.PartRepository;
import com.azya.partstore.models.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public int getAvailableComputersCount() {
        return 0;
    }
}
