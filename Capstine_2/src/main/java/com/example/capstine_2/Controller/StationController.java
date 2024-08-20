package com.example.capstine_2.Controller;


import com.example.capstine_2.Api.ApiResponse;
import com.example.capstine_2.Model.Station;
import com.example.capstine_2.Service.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/station")
public class StationController {
    private final StationService stationService;


    @GetMapping("/get")
    public ResponseEntity getAllStations() {
        return ResponseEntity.status(200).body(stationService.getAllStations());
    }


    @PostMapping("/add")
    public ResponseEntity addStation(@Valid @RequestBody Station station ) {

        stationService.addStation(station);
        return ResponseEntity.status(200).body(new ApiResponse("Station added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStation(@PathVariable Integer id,@Valid @RequestBody Station station) {
        stationService.updateStation(id,station);
        return ResponseEntity.status(200).body(new ApiResponse("Station updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStation(@PathVariable Integer id) {
        stationService.deleteStation(id);
        return ResponseEntity.status(200).body(new ApiResponse("Station deleted successfully"));
    }

    @PutMapping("/status/{stationId}/{tripId}")
    public ResponseEntity updateStatus(@PathVariable Integer stationId, @PathVariable Integer tripId) {
        stationService.updateTripStatus(stationId,tripId);
        return ResponseEntity.status(200).body(new ApiResponse("Trip status updated successfully"));
    }

    @GetMapping("/summary/{stationId}")
    public ResponseEntity getStationSummary(@PathVariable Integer stationId) {
        return ResponseEntity.status(200).body(stationService.summary(stationId));
    }


}
