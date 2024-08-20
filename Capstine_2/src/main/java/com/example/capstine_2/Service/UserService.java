package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Operation;
import com.example.capstine_2.Model.Ticket;
import com.example.capstine_2.Model.Trip;
import com.example.capstine_2.Model.User;
import com.example.capstine_2.Repository.OperationRepository;
import com.example.capstine_2.Repository.TicketRepository;
import com.example.capstine_2.Repository.TripRepository;
import com.example.capstine_2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final OperationService operationService;
    private final OperationRepository operationRepository;
    private final TripRepository tripRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id,User user) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new ApiException("User not found");
        }
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setAge(user.getAge());
        userRepository.save(u);
    }

    public void deleteUser(Integer id) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(u);
    }

    public List<Trip> showTrips(Integer stationFromId , Integer stationToId) {
        List<Trip> trips = tripRepository.findTripByStationFromIdAndStationToId(stationFromId, stationToId);
        if (trips == null) {
            throw new ApiException("No Trips found");
        }
        return trips;
    }

    public List<Ticket> showTickets(Integer tripId){
        return ticketRepository.findTicketsByTripId(tripId);
    }
    
    public void buyTicket(Integer userId, Integer ticketId) {
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }
        Ticket t = ticketRepository.findTicketById(ticketId);
        if (t == null) {
            throw new ApiException("Ticket not found");
        }
        if (!t.getAvailable().equals("Available")){
            throw new ApiException("Ticket is not available to buy");
        }
        Trip trip = tripRepository.findTripById(t.getTripId());
        if (!trip.getStatus().equals("Scheduled")){
            throw new ApiException("Trip not scheduled");
        }
        if (u.getBalance() < t.getPrice()) {
            throw new ApiException("User does not have enough money");
        }
        double points = 0;
        if (t.getLevel().equals("Standard")){
            points = t.getPrice()*0.1;
        }
        if (t.getLevel().equals("First")){
            points = t.getPrice()*0.2;
        }
        if (t.getLevel().equals("Business")){
            points = t.getPrice()*0.3;
        }
        operationService.addOperation(new Operation(null,userId,ticketId, trip.getId(), trip.getStationFromId(), trip.getStationToId(),LocalDateTime.now(),"Buy",t.getPrice(),points));
        u.setBalance(u.getBalance() - t.getPrice());
        u.setPoints(points);
        userRepository.save(u);
        trip.setBuyNumber(trip.getBuyNumber() + 1);
        tripRepository.save(trip);
        t.setAvailable("Not Available");
        ticketRepository.save(t);
    }

    public void returnTicket(Integer userId, Integer ticketId) {
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }
        Ticket t = ticketRepository.findTicketById(ticketId);
        if (t == null) {
            throw new ApiException("Ticket not found");
        }
        Trip trip = tripRepository.findTripById(t.getTripId());
        if (!trip.getStatus().equals("Scheduled")){
            throw new ApiException("Ticket is not scheduled");
        }
        Operation op = operationRepository.findOperationByUserIdAndTicketId(userId, ticketId);
        if (op == null) {
            throw new ApiException("Operation not found");
        }
        if (!op.getType().equals("Buy")){
            throw new ApiException("Operation is not Buy");
        }
        double points = op.getPoints();
        operationService.addOperation(new Operation(null,userId,ticketId, trip.getId(), trip.getStationFromId(), trip.getStationToId(),LocalDateTime.now(),"Return",t.getPrice(),points));
        u.setBalance(u.getBalance() + t.getPrice());
        u.setPoints(u.getPoints()-points);
        userRepository.save(u);
        trip.setBuyNumber(trip.getBuyNumber() - 1);
        tripRepository.save(trip);
        t.setAvailable("Available");
        ticketRepository.save(t);
    }


    public void convert(Integer userId){
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }
        if (u.getPoints() < 100){
            throw new ApiException("User does not have enough points to convert");
        }
        u.setBalance(u.getBalance() + u.getPoints());
        u.setPoints(0);
        userRepository.save(u);
    }

    public void transfer(Integer userId1, Integer userId2 , double points){
        User u1 = userRepository.findUserById(userId1);
        User u2 = userRepository.findUserById(userId2);
        if (u1 == null) {
            throw new ApiException("First User not found");
        }
        if (u2 == null) {
            throw new ApiException("Second User not found");
        }
        if (u1.getPoints() < points){
            throw new ApiException("First User does not have enough points to transfer");
        }
        u1.setPoints(u1.getPoints() - points);
        u2.setPoints(u2.getPoints() + points);
        userRepository.save(u1);
        userRepository.save(u2);
    }

    public void deposit(Integer userId , double money){
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }
        u.setBalance(u.getBalance() + money);
        userRepository.save(u);
    }

    public List<Operation> history(Integer userId) {
        return operationRepository.findOperationByUserId(userId);
    }

    public String summary(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        List<Operation> list = operationRepository.findOperationByUserId(userId);
        if (list == null) {
            throw new ApiException("Operations not found");
        }
        int operationNumber = list.size();
        int buyNumber = 0;
        int returnNumber = 0;
        double total = 0;
        for (int i = 0 ; i < list.size() ; i++ ) {
            Operation op = list.get(i);
            if (op.getType().equals("Buy")){
                buyNumber = buyNumber + 1;
                total = total + ticketRepository.findTicketById(op.getTicketId()).getPrice();
            }
            if (op.getType().equals("Return")){
                returnNumber = returnNumber + 1;
                total = total - ticketRepository.findTicketById(op.getTicketId()).getPrice();
            }
        }
        return "{ "+ user.getName()+" , Operation Number : "+operationNumber+" , Buy Number : "+buyNumber+" , Return Number : "+returnNumber+" , Total Money spent "+total+" }";
    }

    public void changeSeat(Integer userId , Integer ticketId , String seat){
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }
        Ticket t = ticketRepository.findTicketById(ticketId);
        if (t == null) {
            throw new ApiException("Ticket not found");
        }
        Operation op = operationRepository.findOperationByUserIdAndTicketId(userId, ticketId);
        if (op == null) {
            throw new ApiException("Operation not found");
        }
        if (!op.getType().equals("Buy")){
            throw new ApiException("Operation is not Buy");
        }
        Trip trip = tripRepository.findTripById(t.getTripId());
        if (!trip.getStatus().equals("Scheduled")){
            throw new ApiException("Trip not scheduled");
        }
        Ticket ticket = ticketRepository.getTicketSeat(seat);
        if (!ticket.getAvailable().equals("Available")){
            throw new ApiException("Ticket not available");
        }
        if (!ticket.getLevel().equals(t.getLevel())){
            throw new ApiException("level is different");
        }
        ticket.setAvailable("Not Available");
        ticketRepository.save(ticket);
        op.setTicketId(ticket.getId());
        operationRepository.save(op);
        t.setAvailable("Available");
        ticketRepository.save(t);
    }













}
