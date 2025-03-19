package com.sis.gescobank.dto.account;

import com.sis.gescobank.datamodel.Account;
import com.sis.gescobank.datamodel.AccountStatusEnum;
import com.sis.gescobank.datamodel.AccountTypeEnum;
import com.sis.gescobank.datamodel.CurrencyEnum;
import com.sis.gescobank.dto.customer.CustomerDTO;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.isNull;

public class AccountDTO {
    private Long id;
    private String code;
    private LocalDate createdAt;
    private double balance;
    private AccountStatusEnum status;
    private CurrencyEnum currency;
    private CustomerDTO customer;
    private AccountTypeEnum type;

    public AccountDTO() {
    }

    public static AccountDTO fromEntity(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setCode(account.getCode());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setCurrency(account.getCurrency());
        accountDTO.setCustomer(CustomerDTO.fromEntity(account.getCustomer()));
        return accountDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AccountStatusEnum status) {
        this.status = status;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public AccountTypeEnum getType() {
        return type;
    }

    public void setType(AccountTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Double.compare(balance, that.balance) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(createdAt, that.createdAt) &&
                status == that.status &&
                currency == that.currency &&
                Objects.equals(customer, that.customer) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, createdAt, balance, status, currency, customer, type);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", balance=" + balance +
                ", status=" + status +
                ", currency=" + currency +
                ", type='" + type + '\'';
    }
}
