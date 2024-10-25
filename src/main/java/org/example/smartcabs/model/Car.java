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
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CarId;

    @Column(nullable = false)
    CarType carType;

    @Column(nullable = false)
    String carCompany;

    @Column(nullable = false)
    String numberPlate;

    String color;
}
