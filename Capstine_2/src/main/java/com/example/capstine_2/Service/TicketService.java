package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Ticket;
import com.example.capstine_2.Model.Train;
import com.example.capstine_2.Model.Trip;
import com.example.capstine_2.Repository.TicketRepository;
import com.example.capstine_2.Repository.TrainRepository;
import com.example.capstine_2.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final TrainRepository trainRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void add(Ticket ticket) {
        Train train = trainRepository.findTrainById(ticket.getTrainId());
        if (train == null) {
            throw new ApiException("Train not exist");
        }
        Trip trip = tripRepository.findTripById(ticket.getTripId());
        if (trip == null) {
            throw new ApiException("Trip not exist");
        }
        if (trip.getCapacity()==0){
            throw new ApiException("Trip is full");
        }
        ticketRepository.save(ticket);
        trip.setCapacity(trip.getCapacity()-1);
        tripRepository.save(trip);
    }

    public void update(Integer id,Ticket ticket) {
        Ticket t = ticketRepository.findTicketById(id);
        if (t == null) {
            throw new ApiException("Ticket not found");
        }
        t.setSeat(ticket.getSeat());
        t.setPrice(ticket.getPrice());
        t.setLevel(ticket.getLevel());
        ticketRepository.save(t);
    }

    public void delete(Integer id) {
        Ticket t = ticketRepository.findTicketById(id);
        if (t == null) {
            throw new ApiException("Ticket not found");
        }
        ticketRepository.delete(t);
    }
}
