package org.example.smartcabs.repository;

import org.example.smartcabs.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findLocationByLatitudeAndLongitude(int latitude, int longitude);
}
