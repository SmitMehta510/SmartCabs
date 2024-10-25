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
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tripId;

    @ManyToOne
    User user;

    @OneToOne
    Cab cab;

    @ManyToOne
    Location startLocation;



}
