package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketById(Integer id);
    List<Ticket> findTicketsByTripId(Integer id);
    @Query("select t from Ticket t where t.seat = ?1")
    Ticket getTicketSeat(String seat);
}
