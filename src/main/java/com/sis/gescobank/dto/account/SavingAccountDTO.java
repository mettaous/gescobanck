package com.sis.gescobank.dto.account;

import com.sis.gescobank.datamodel.AccountTypeEnum;
import com.sis.gescobank.datamodel.SavingAccount;

import java.util.Objects;

import static java.util.Objects.isNull;

public class SavingAccountDTO extends AccountDTO {

    private double interestRate;

    public static SavingAccountDTO fromEntity(SavingAccount savingAccount) {
        AccountDTO accountDTO = AccountDTO.fromEntity(savingAccount);
        SavingAccountDTO savingAccountDTO = (SavingAccountDTO) accountDTO;
        savingAccountDTO.setInterestRate(savingAccount.getInterestRate());
        savingAccountDTO.setType(AccountTypeEnum.CURRENT_ACCOUNT);
        return savingAccountDTO;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SavingAccountDTO that = (SavingAccountDTO) o;
        return Double.compare(interestRate, that.interestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' +
                super.toString() +
                "interestRate=" + interestRate +
                ')';
    }
}
