package com.example.capstine_2.Controller;

import com.example.capstine_2.Api.ApiResponse;
import com.example.capstine_2.Model.User;
import com.example.capstine_2.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id , @Valid @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    @GetMapping("get/trip/{stationFromId}/{stationToId}")
    public ResponseEntity getTrips(@PathVariable Integer stationFromId, @PathVariable Integer stationToId){
        return ResponseEntity.status(200).body(userService.showTrips(stationFromId, stationToId));
    }

    @GetMapping("/get/ticket/{tripId}")
    public ResponseEntity getAllTickets(@PathVariable Integer tripId){
        return ResponseEntity.status(200).body(userService.showTickets(tripId));
    }

    @PutMapping("/buy/{userId}/{ticketId}")
    public ResponseEntity buyTicket(@PathVariable Integer userId, @PathVariable Integer ticketId){
        userService.buyTicket(userId, ticketId);
        return ResponseEntity.status(200).body(new ApiResponse("User buy ticket successfully"));
    }

    @PutMapping("/return/{userId}/{ticketId}")
    public ResponseEntity returnTicket(@PathVariable Integer userId, @PathVariable Integer ticketId){
        userService.returnTicket(userId, ticketId);
        return ResponseEntity.status(200).body(new ApiResponse("User returned successfully"));
    }

    @PutMapping("/deposit/{userId}/{money}")
    public ResponseEntity deposit(@PathVariable Integer userId, @PathVariable Integer money){
        userService.deposit(userId, money);
        return ResponseEntity.status(200).body(new ApiResponse("User deposit successfully"));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity history(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(userService.history(userId));
    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity summary(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(userService.summary(userId));
    }

    @PutMapping("/change/{userId}/{ticketId}/{seat}")
    public ResponseEntity changeSeat(@PathVariable Integer userId, @PathVariable Integer ticketId, @PathVariable String seat){
        userService.changeSeat(userId, ticketId, seat);
        return ResponseEntity.status(200).body(new ApiResponse("User changed seat successfully"));
    }

    @PutMapping("/convert/{userId}")
    public ResponseEntity convertUser(@PathVariable Integer userId){
        userService.convert(userId);
        return ResponseEntity.status(200).body(new ApiResponse("User converted points successfully"));
    }

    @PutMapping("/transfer/{userId1}/{userId2}/{points}")
    public ResponseEntity transferUser(@PathVariable Integer userId1, @PathVariable Integer userId2, @PathVariable double points){
        userService.transfer(userId1, userId2, points);
        return ResponseEntity.status(200).body(new ApiResponse("User transferred points successfully"));
    }





}
