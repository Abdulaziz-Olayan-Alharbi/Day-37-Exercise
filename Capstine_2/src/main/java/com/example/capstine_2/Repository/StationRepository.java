package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    Station findStationById(Integer id);
    Station findStationByName(String name);
}
