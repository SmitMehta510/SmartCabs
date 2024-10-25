package org.example.smartcabs.service.impl;

import org.example.smartcabs.model.Car;
import org.example.smartcabs.model.CarType;
import org.example.smartcabs.repository.CarRepository;
import org.example.smartcabs.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Override
    public void populateCar() {
        Car car1 = new Car();
        car1.setCarType(CarType.SEDAN);
        car1.setCarCompany("Tata");
        car1.setColor("Red");
        car1.setNumberPlate("0000");

        Car car2 = new Car();
        car2.setCarType(CarType.HATCHBACK);
        car2.setCarCompany("Maruti");
        car2.setColor("White");
        car2.setNumberPlate("1111");

        Car car3 = new Car();
        car3.setCarType(CarType.SUV);
        car3.setCarCompany("Kia");
        car3.setColor("Black");
        car3.setNumberPlate("2222");

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);

    }

    @Override
    public List<Car> getCarList(){
        return carRepository.findAll();
    }
}
