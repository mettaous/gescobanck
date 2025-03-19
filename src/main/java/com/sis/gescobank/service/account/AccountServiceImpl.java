package com.sis.gescobank.service.account;

import com.sis.gescobank.datamodel.*;
import com.sis.gescobank.datarepository.AccountRepository;
import com.sis.gescobank.dto.account.AccountDTO;
import com.sis.gescobank.dto.account.CurrentAccountDTO;
import com.sis.gescobank.dto.account.SavingAccountDTO;
import com.sis.gescobank.dto.operation.OperationDTO;
import com.sis.gescobank.exception.DataNotCoherentBusinessException;
import com.sis.gescobank.exception.DataNotFoundBusinessException;
import com.sis.gescobank.exception.ValidationBusinessException;
import com.sis.gescobank.service.customer.CustomerService;
import com.sis.gescobank.service.operation.OperationService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class AccountServiceImpl implements AccountService {

    private final CustomerService customerService;
    private final OperationService operationService;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerService customerService,
                              OperationService operationService) {
        this.customerService = customerService;
        this.operationService = operationService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundBusinessException(Account.class.getSimpleName(),
                        String.valueOf(accountId)));
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        return convertToDTO(findAccountById(accountId));
    }

    @Override
    public Account findAccountByCode(String accountCode) {
        return accountRepository.getByCode(accountCode)
                .orElseThrow(() -> new DataNotFoundBusinessException(Account.class.getSimpleName(),
                        Collections.singletonMap(Account.PROPERTY_CODE, accountCode)));
    }

    @Override
    public AccountDTO getAccountByCode(String accountCode) {
        return convertToDTO(findAccountByCode(accountCode));
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        checkAccountDataValidity(currentAccountDTO);

        Customer customer = customerService.findCustomerById(currentAccountDTO.getId());

        CurrentAccount currentAccount = new CurrentAccount(currentAccountDTO.getBalance(),
                currentAccountDTO.getCurrency(),
                currentAccountDTO.getOverdraft());
        currentAccount.setCustomer(customer);

        return CurrentAccountDTO.fromEntity(accountRepository.save(currentAccount));
    }

    @Override
    public SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) {
        checkAccountDataValidity(savingAccountDTO);

        Customer customer = customerService.findCustomerById(savingAccountDTO.getId());

        SavingAccount savingAccount = new SavingAccount(savingAccountDTO.getBalance(),
                savingAccountDTO.getCurrency(),
                savingAccountDTO.getInterestRate());
        savingAccount.setCustomer(customer);

        return SavingAccountDTO.fromEntity(accountRepository.save(savingAccount));
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public void debit(String accountCode, double amount, String description) {
        Account account = findAccountByCode(accountCode);
        if (account.getBalance() < amount) {
            throw new DataNotCoherentBusinessException(String.format("The balance %.2f is less than the amount %.2f",
                    account.getBalance(),
                    amount));
        }
        operationService.saveOperation(account, amount, OperationTypeEnum.DEBIT, description);
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void credit(String accountCode, double amount, String description) {
        Account account = findAccountByCode(accountCode);
        operationService.saveOperation(account, amount, OperationTypeEnum.CREDIT, description);
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void transfer(String accountCodeFrom, String accountCodeTo, double amount, String description) {
        debit(accountCodeFrom, amount, description);
        credit(accountCodeTo, amount, description);
    }

    @Override
    public List<OperationDTO> accountOperations(String accountCode) {
        return operationService.findOperationsByAccountCode(accountCode);
    }

    private void checkAccountDataValidity(AccountDTO accountDTO) {
        if (isNull(accountDTO.getCurrency())) {
            throw new ValidationBusinessException(Account.PROPERTY_CURRENCY + " is required");
        }
        if (isNull(accountDTO.getCustomer())) {
            throw new ValidationBusinessException("Customer is required");
        }
    }

    private AccountDTO convertToDTO(Account account) {
        return (account instanceof CurrentAccount currentAccount)
                ? CurrentAccountDTO.fromEntity(currentAccount)
                : SavingAccountDTO.fromEntity((SavingAccount) account);
    }

}
