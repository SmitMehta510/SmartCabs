package org.example.smartcabs.repository;

import org.example.smartcabs.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

}
