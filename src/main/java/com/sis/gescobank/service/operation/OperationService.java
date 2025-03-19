package com.sis.gescobank.service.operation;

import com.sis.gescobank.datamodel.Account;
import com.sis.gescobank.datamodel.Operation;
import com.sis.gescobank.datamodel.OperationTypeEnum;
import com.sis.gescobank.dto.operation.OperationDTO;

import java.util.List;

public interface OperationService {

    void saveOperation(Account account, double amount, OperationTypeEnum type, String description);

    List<OperationDTO> findOperationsByAccountCode(String accountCode);
}
