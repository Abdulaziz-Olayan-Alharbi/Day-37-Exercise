package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.*;
import com.example.capstine_2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RailwaysService {
    private final RailwaysRepository railwaysRepository;
    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;
    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    public List<Railways> getAllRailways() {
        return railwaysRepository.findAll();
    }

    public void add(Railways railways) {
        railwaysRepository.save(railways);
    }

    public void update(Integer id,Railways railways) {
        Railways r = railwaysRepository.findRailwaysById(id);
        if (r == null){
            throw new ApiException("Railways not found");
        }
        r.setName(railways.getName());
        r.setCountry(railways.getCountry());
        r.setStationsNumber(railways.getStationsNumber());
        railwaysRepository.save(r);
    }

    public void delete(Integer id) {
        Railways r = railwaysRepository.findRailwaysById(id);
        if (r == null){
            throw new ApiException("Railways not found");
        }
        railwaysRepository.delete(r);
    }

    public void cancelTrip(Integer railwaysId , Integer tripId) {
        Railways r = railwaysRepository.findRailwaysById(railwaysId);
        if (r == null){
            throw new ApiException("Railways not found");
        }
        Trip t = tripRepository.findTripById(tripId);
        if (t == null){
            throw new ApiException("Trip not found");
        }
        if (!t.getRailwaysId().equals(railwaysId)){
            throw new ApiException("Railways id mismatch");
        }
        if (!t.getStatus().equals("Scheduled")){
            throw new ApiException("Trip must be scheduled to cancel it");
        }
        Station station1 = stationRepository.findStationById(t.getStationFromId());
        station1.setTripCapacity(station1.getTripCapacity() + 1);
        stationRepository.save(station1);
        Station station2 = stationRepository.findStationById(t.getStationToId());
        station2.setTripCapacity(station2.getTripCapacity() + 1);
        stationRepository.save(station2);
        Train train = trainRepository.findTrainById(t.getTrainId());
        train.setCapacity(train.getCapacity()+t.getCapacity()+t.getBuyNumber());
        trainRepository.save(train);
        t.setStatus("Canceled");
        tripRepository.save(t);
        List<Operation> op = operationRepository.findOperationByTripId(tripId);
        for (Operation o : op){
            User user = userRepository.findUserById(o.getUserId());
            if (o.getType().equals("Buy")){
                user.setBalance(user.getBalance()+o.getPrice());
                userRepository.save(user);
            }
        }
    }

    public void addTripCapacity(Integer railwaysId , Integer tripId , int capacity) {
        Railways r = railwaysRepository.findRailwaysById(railwaysId);
        if (r == null){
            throw new ApiException("Railways not found");
        }
        Trip t = tripRepository.findTripById(tripId);
        if (t == null){
            throw new ApiException("Trip not found");
        }
        if (!t.getRailwaysId().equals(railwaysId)){
            throw new ApiException("Railways id mismatch");
        }
        if (!t.getStatus().equals("Scheduled")){
            throw new ApiException("Trip must be scheduled to add capacity");
        }
        Train train = trainRepository.findTrainById(t.getTrainId());
        if (train.getCapacity() - capacity < 0 ){
            throw new ApiException("Trip capacity exceeds capacity");
        }
        t.setCapacity(t.getCapacity()+capacity);
        tripRepository.save(t);
        train.setCapacity(train.getCapacity()-capacity);
        trainRepository.save(train);
    }

    public String summary(Integer railwaysId) {
        Railways r = railwaysRepository.findRailwaysById(railwaysId);
        if (r == null){
            throw new ApiException("Railways not found");
        }
        int tripCount = 0;
        double totalRevenue = 0.0;
        int sellTickets = 0;
        int notSoldTickets = 0;
        List<Trip> trips = tripRepository.findTripsByRailwaysId(railwaysId);
        if (trips == null){
            throw new ApiException("Trips not found");
        }
        for (Trip t : trips){
            if (t.getStatus().equals("In Transit")||t.getStatus().equals("Terminated")){
                tripCount++;
                List<Ticket> tickets = ticketRepository.findTicketsByTripId(t.getId());
                for (Ticket ticket : tickets){
                    if (ticket.getAvailable().equals("Available")){
                        notSoldTickets++;
                    }else {
                        sellTickets++;
                        totalRevenue = totalRevenue + ticket.getPrice();
                    }
                }
            }
        }
        return "{ "+r.getName()+", Trips number : "+tripCount+" Total Revenue : "+totalRevenue+", Sold Tickets : "+sellTickets+", Not Sold Tickets : "+notSoldTickets+" }";
    }


}
