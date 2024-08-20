package com.example.capstine_2.Service;


import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Train;
import com.example.capstine_2.Repository.RailwaysRepository;
import com.example.capstine_2.Repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final TrainRepository trainRepository;
    private final RailwaysRepository railwaysRepository;

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public void addTrain(Train train) {
        if (!railwaysRepository.existsById(train.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        trainRepository.save(train);
    }

    public void updateTrain(Integer id,Train train) {
        Train t = trainRepository.findTrainById(id);
        if ( t == null){
            throw new ApiException("Train not found");
        }
        if (!railwaysRepository.existsById(train.getRailwaysId())) {
            throw new ApiException("Railway does not exist");
        }
        t.setRailwaysId(train.getRailwaysId());
        t.setCapacity(train.getCapacity());
        trainRepository.save(t);
    }

    public void deleteTrain(Integer id) {
        Train t = trainRepository.findTrainById(id);
        if ( t == null){
            throw new ApiException("Train not found");
        }
        trainRepository.delete(t);
    }
}
