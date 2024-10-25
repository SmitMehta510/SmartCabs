package org.example.smartcabs.dto;

import lombok.Data;
import lombok.Getter;
import org.example.smartcabs.model.Location;

@Data
@Getter
public class BookingRequest {

    Long userId;
    Location srcLocation;

}
