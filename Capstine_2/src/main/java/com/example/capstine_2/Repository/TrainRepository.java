package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
    Train findTrainById(Integer id);
}
