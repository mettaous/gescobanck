package com.sis.gescobank.service.operation;

import com.sis.gescobank.datamodel.Account;
import com.sis.gescobank.datamodel.Operation;
import com.sis.gescobank.datamodel.OperationTypeEnum;
import com.sis.gescobank.datarepository.OperationRepository;
import com.sis.gescobank.dto.operation.OperationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void saveOperation(Account account, double amount, OperationTypeEnum type, String description) {
        Operation operation = new Operation(amount, type);
        operation.setDescription(description);
        operation.setAccount(account);
        operationRepository.save(operation);
    }

    @Override
    public List<OperationDTO> findOperationsByAccountCode(String accountCode) {
        return operationRepository.findByAccountCode(accountCode)
                .stream()
                .map(OperationDTO::fromEntity)
                .toList();
    }
}
