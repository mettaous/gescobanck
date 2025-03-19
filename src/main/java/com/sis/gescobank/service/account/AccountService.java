package com.sis.gescobank.service.account;

import com.sis.gescobank.datamodel.Account;
import com.sis.gescobank.dto.account.AccountDTO;
import com.sis.gescobank.dto.account.CurrentAccountDTO;
import com.sis.gescobank.dto.account.SavingAccountDTO;
import com.sis.gescobank.dto.operation.OperationDTO;

import java.util.List;

public interface AccountService {

    Account findAccountById(Long accountId);

    AccountDTO getAccountById(Long accountId);

    Account findAccountByCode(String accountCode);

    AccountDTO getAccountByCode(String accountCode);

    CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO);

    SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO);

    List<AccountDTO> getAccounts();

    void debit(String accountCode, double amount, String description);

    void credit(String accountCode, double amount, String description);

    void transfer(String accountCodeFrom,
                  String accountCodeTo,
                  double amount,
                  String description);

    List<OperationDTO> accountOperations(String accountCode);
}
