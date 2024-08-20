package com.example.capstine_2.Controller;

import com.example.capstine_2.Api.ApiResponse;
import com.example.capstine_2.Model.Ticket;
import com.example.capstine_2.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get")
    public ResponseEntity getAllTickets() {
        return ResponseEntity.status(200).body(ticketService.getAllTickets());
    }

    @PostMapping("/add")
    public ResponseEntity addTicket(@Valid @RequestBody Ticket ticket) {
        ticketService.add(ticket);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTicket(@PathVariable int id,@Valid @RequestBody Ticket ticket ) {
        ticketService.update(id, ticket);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTicket(@PathVariable int id) {
        ticketService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket deleted successfully"));
    }












}
