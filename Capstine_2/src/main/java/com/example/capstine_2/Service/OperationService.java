package com.example.capstine_2.Service;

import com.example.capstine_2.Api.ApiException;
import com.example.capstine_2.Model.Operation;
import com.example.capstine_2.Repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public void addOperation(Operation operation) {
        operationRepository.save(operation);
    }

    public void deleteOperation(Integer id,Operation operation) {
        Operation o = operationRepository.findOperationById(id);
        if (o == null) {
            throw new ApiException("Operation not found");
        }
        operationRepository.delete(o);
    }
}
