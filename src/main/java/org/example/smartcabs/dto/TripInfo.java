package org.example.smartcabs.dto;

import lombok.Data;
import lombok.Setter;
import org.example.smartcabs.model.CarType;
import org.example.smartcabs.model.Location;

@Data
@Setter
public class TripInfo {

    private CarType carType;
    private String carCompany;
    private String numberPlate;
    Location startLocation;
    Location cabCurrentLocation;
}
