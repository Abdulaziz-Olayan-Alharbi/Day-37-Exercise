package com.example.capstine_2.Controller;


import com.example.capstine_2.Api.ApiResponse;
import com.example.capstine_2.Model.Railways;
import com.example.capstine_2.Service.RailwaysService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/railways")
public class RailwaysController {
    private final RailwaysService railwaysService;

    @GetMapping("/get")
    public ResponseEntity getRailways() {
        return ResponseEntity.status(200).body(railwaysService.getAllRailways());
    }

    @PostMapping("/add")
    public ResponseEntity addRailways(@Valid @RequestBody Railways railways ) {
        railwaysService.add(railways);
        return ResponseEntity.status(200).body(new ApiResponse("Railway added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRailways(@PathVariable Integer id,@Valid @RequestBody Railways railways ) {
        railwaysService.update(id, railways);
        return ResponseEntity.status(200).body(new ApiResponse("Railway updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRailways(@PathVariable Integer id) {
        railwaysService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Railway deleted successfully"));
    }

    @PutMapping("/cancel/{railwaysId}/{tripId}")
    public ResponseEntity cancelTrip(@PathVariable Integer railwaysId, @PathVariable Integer tripId) {
        railwaysService.cancelTrip(railwaysId, tripId);
        return ResponseEntity.status(200).body(new ApiResponse("Trip canceled successfully"));
    }

    @GetMapping("/summary/{railwaysId}")
    public ResponseEntity getSummary(@PathVariable Integer railwaysId) {
        return ResponseEntity.status(200).body(railwaysService.summary(railwaysId));
    }

    @PutMapping("/capacity/{railwaysId}/{tripId}/{capacity}")
    public ResponseEntity addTripCapacity(@PathVariable Integer railwaysId, @PathVariable Integer tripId, @PathVariable int capacity) {
        railwaysService.addTripCapacity(railwaysId, tripId, capacity);
        return ResponseEntity.status(200).body(new ApiResponse("Trip capacity added successfully"));
    }












}
