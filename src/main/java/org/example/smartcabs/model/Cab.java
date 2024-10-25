package org.example.smartcabs.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cabId;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    Car car;

    boolean isActive;

    Status status;

    @ManyToOne
    @JoinColumn(name = "current_location", nullable = false)
    Location currentLocation;

}
