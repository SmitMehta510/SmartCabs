package org.example.smartcabs.service.impl;

import org.example.smartcabs.model.Cab;
import org.example.smartcabs.repository.CabRepository;
import org.example.smartcabs.service.CabService;
import org.example.smartcabs.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabServiceImpl implements CabService {


    @Autowired
    LocationService locationService;

    @Autowired
    CabRepository cabRepository;

    @Override
    public List<Cab> getCabLocation() {
        return locationService.getCabLocations();
    }

    @Override
    public List<Cab> getCabList() {
        return cabRepository.findAll();
    }
}
