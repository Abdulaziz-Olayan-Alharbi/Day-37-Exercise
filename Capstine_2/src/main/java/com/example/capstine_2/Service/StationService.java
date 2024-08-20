package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Station;
import com.example.capstine_2.Model.Ticket;
import com.example.capstine_2.Model.Train;
import com.example.capstine_2.Model.Trip;
import com.example.capstine_2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final RailwaysRepository railwaysRepository;
    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public void addStation(Station station) {
        if (!railwaysRepository.existsById(station.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        stationRepository.save(station);
    }

    public void updateStation(Integer id,Station station) {
        Station s = stationRepository.findStationById(id);
        if (s == null){
            throw new ApiException("Train not found");
        }
        if (!railwaysRepository.existsById(station.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        s.setName(station.getName());
        s.setLocation(station.getLocation());
        s.setTripCapacity(station.getTripCapacity());
    }

    public void deleteStation(Integer id) {
        Station s = stationRepository.findStationById(id);
        if (s == null){
            throw new ApiException("Train not found");
        }
        stationRepository.delete(s);
    }

    public void updateTripStatus(Integer StationId , Integer tripId) {
        Station s = stationRepository.findStationById(StationId);
        if (s == null){
            throw new ApiException("Station not found");
        }
        Trip t = tripRepository.findTripById(tripId);
        if (t == null){
            throw new ApiException("Trip not found");
        }
        if (t.getStatus().equals("Terminated")){
            throw new ApiException("Trip is Terminated ");
        }
        Train train = trainRepository.findTrainById(t.getTrainId());
        if (t.getStatus().equals("In Transit")){
            if (!s.getId().equals(t.getStationToId())){
                throw new ApiException("change In Transit trips only if Station is end point ");
            }
            t.setStatus("Terminated");
            tripRepository.save(t);
            s.setTripCapacity(s.getTripCapacity()+1);
            stationRepository.save(s);
            train.setCapacity(train.getCapacity()+t.getCapacity());
            trainRepository.save(train);
        }
        if (t.getStatus().equals("Scheduled")){
            if (!s.getId().equals(t.getStationFromId())){
                throw new ApiException("change scheduled trips only if Station is start point ");
            }
            t.setStatus("In Transit");
            tripRepository.save(t);
            s.setTripCapacity(s.getTripCapacity()+1);
            stationRepository.save(s);
        }
        if (t.getStatus().equals("Canceled")){
            throw new ApiException("Trip is Canceled ");
        }
    }

    public String summary(Integer stationId) {
        Station s = stationRepository.findStationById(stationId);
        int totalTrips = 0;
        int totalStartPoints = 0;
        int totalEndPoints = 0;
        double totalRevenue = 0;
        int sellTickets = 0;
        int notSoldTickets = 0;
        List<Trip> trips = tripRepository.getTripsOfStation(stationId);
        if (trips == null){
            throw new ApiException("Trips not found");
        }
        for (Trip t : trips){
            if (t.getStatus().equals("In Transit")||t.getStatus().equals("Terminated")){
                totalTrips++;
                if (t.getStationFromId().equals(stationId)){
                    totalStartPoints++;
                }
                if (t.getStationToId().equals(stationId)){
                    totalEndPoints++;
                }
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

        return "{ "+s.getName()+", Trips number : "+totalTrips+", Start Point Trips Number : "+totalStartPoints+", End Point Trips number"+totalEndPoints+" Total Revenue : "+totalRevenue+", Sold Tickets : "+sellTickets+", Not Sold Tickets : "+notSoldTickets+" }";

    }
}
