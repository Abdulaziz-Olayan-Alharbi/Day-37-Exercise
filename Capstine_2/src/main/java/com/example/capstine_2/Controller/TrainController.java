package com.example.capstine_2.Controller;


import com.example.capstine_2.Model.Train;
import com.example.capstine_2.Service.TrainService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/train")
public class TrainController {
    private final TrainService trainService;

    @GetMapping("/get")
    public ResponseEntity getAllTrain() {
        return ResponseEntity.status(200).body(trainService.getAllTrains());
    }

    @PostMapping("/add")
    public ResponseEntity addTrain(@Valid @RequestBody Train train) {
        trainService.addTrain(train);
        return ResponseEntity.status(200).body("Train added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTrain(@PathVariable Integer id,@Valid @RequestBody Train train ) {
        trainService.updateTrain(id, train);
        return ResponseEntity.status(200).body("Train updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTrain(@PathVariable Integer id) {
        trainService.deleteTrain(id);
        return ResponseEntity.status(200).body("Train deleted successfully");
    }
}
