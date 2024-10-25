package org.example.smartcabs.service;

import org.example.smartcabs.model.Car;

import java.util.List;

public interface CarService {

    void populateCar();
    List<Car> getCarList();
}
