package org.example.smartcabs.service;

import org.example.smartcabs.model.Cab;

import java.util.List;

public interface CabService {

    List<Cab> getCabLocation();

    List<Cab> getCabList();
}
