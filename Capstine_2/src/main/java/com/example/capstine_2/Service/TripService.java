package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Station;
import com.example.capstine_2.Model.Train;
import com.example.capstine_2.Model.Trip;
import com.example.capstine_2.Repository.RailwaysRepository;
import com.example.capstine_2.Repository.StationRepository;
import com.example.capstine_2.Repository.TrainRepository;
import com.example.capstine_2.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final RailwaysRepository railwaysRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public void addTrip(Trip trip) {
        if (!railwaysRepository.existsById(trip.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        Train train = trainRepository.findTrainById(trip.getTrainId());
        if (train == null) {
            throw new ApiException("Train does not exist");
        }
        if (train.getCapacity() - trip.getCapacity() < 0){
            throw new ApiException("Train capacity is not enough");
        }
        Station station1 = stationRepository.findStationById(trip.getStationFromId());
        if (station1 == null) {
            throw new ApiException("First Station does not exist");
        }
        if (station1.getTripCapacity() == 0 ){
            throw new ApiException("First Station does not have enough capacity");
        }
        Station station2 = stationRepository.findStationById(trip.getStationToId());
        if (station2 == null) {
            throw new ApiException("Second Station does not exist");
        }
        if (station2.getTripCapacity() == 0 ){
            throw new ApiException("Second Station does not have enough capacity");
        }
        station1.setTripCapacity(station1.getTripCapacity()-1);
        stationRepository.save(station1);
        station2.setTripCapacity(station2.getTripCapacity()-1);
        stationRepository.save(station2);
        train.setCapacity(train.getCapacity()- trip.getCapacity());
        trainRepository.save(train);
        tripRepository.save(trip);
    }

    public void updateTrip(Integer id,Trip trip) {
        Trip t = tripRepository.findTripById(id);
        if (t == null) {
            throw new ApiException("Trip does not exist");
        }
        if (!railwaysRepository.existsById(trip.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        if (!trainRepository.existsById(trip.getTrainId())) {
            throw new ApiException("Train does not exist");
        }
        Station station1 = stationRepository.findStationById(trip.getStationFromId());
        if (station1 == null) {
            throw new ApiException("First Station does not exist");
        }
        if (station1.getTripCapacity() < 0 ){
            throw new ApiException("First Station does not have enough capacity");
        }
        Station station2 = stationRepository.findStationById(trip.getStationToId());
        if (station2 == null) {
            throw new ApiException("Second Station does not exist");
        }
        if (station2.getTripCapacity() < 0 ){
            throw new ApiException("Second Station does not have enough capacity");
        }
        t.setStartTime(trip.getStartTime());
        t.setEndTime(trip.getEndTime());
        tripRepository.save(t);
    }

    public void deleteTrip(Integer id) {
        Trip t = tripRepository.findTripById(id);
        if (t == null) {
            throw new ApiException("Trip does not exist");
        }
        tripRepository.delete(t);
    }
}
