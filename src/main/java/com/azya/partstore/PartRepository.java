package com.azya.partstore;

import com.azya.partstore.models.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepository extends CrudRepository<Part,Long> {
}
