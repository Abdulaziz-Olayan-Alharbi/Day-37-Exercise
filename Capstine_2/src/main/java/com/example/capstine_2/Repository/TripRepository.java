package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    Trip findTripById(Integer id);
    List<Trip> findTripByStationFromIdAndStationToId(Integer fromId, Integer toId);
    @Query("select t from Trip t where t.stationFromId = ?1 or t.stationToId = ?1")
    List<Trip> getTripsOfStation(Integer StationId);
    List<Trip> findTripsByRailwaysId(Integer id);
}
