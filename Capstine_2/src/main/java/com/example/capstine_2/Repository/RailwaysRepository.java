package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Railways;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailwaysRepository extends JpaRepository<Railways, Integer> {
    Railways findRailwaysById(Integer id);
}
