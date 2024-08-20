package com.example.capstine_2.Controller;


import com.example.capstine_2.Api.ApiResponse;
import com.example.capstine_2.Model.Trip;
import com.example.capstine_2.Service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping("/get")
    public ResponseEntity getAllTrips() {
        return ResponseEntity.status(200).body(tripService.getAllTrips());
    }

    @PostMapping("/add")
    public ResponseEntity addTrip(@Valid @RequestBody Trip trip ) {
        tripService.addTrip(trip);
        return ResponseEntity.status(200).body(new ApiResponse("Trip added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTrip(@PathVariable int id,@Valid @RequestBody Trip trip) {
        tripService.updateTrip(id, trip);
        return ResponseEntity.status(200).body(new ApiResponse("Trip updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTrip(@PathVariable int id) {
        tripService.deleteTrip(id);
        return ResponseEntity.status(200).body(new ApiResponse("Trip deleted successfully"));
    }
















}
