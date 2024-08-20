package com.example.capstine_2.Repository;

import com.example.capstine_2.Model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
    Operation findOperationById(Integer id);
    Operation findOperationByUserIdAndTicketId(Integer userId, Integer ticketId);
    List<Operation> findOperationByUserId(Integer userId);
    List<Operation> findOperationByTripId(Integer tripId);
}
