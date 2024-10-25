package org.example.smartcabs.repository;

import org.example.smartcabs.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CabRepository extends JpaRepository<Cab, Long> {
}
