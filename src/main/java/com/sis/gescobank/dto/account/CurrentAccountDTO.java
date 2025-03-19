package com.sis.gescobank.dto.account;

import com.sis.gescobank.datamodel.AccountTypeEnum;
import com.sis.gescobank.datamodel.CurrentAccount;

import java.util.Objects;

import static java.util.Objects.isNull;

public class CurrentAccountDTO extends AccountDTO {

    private double overdraft;

    public static CurrentAccountDTO fromEntity(CurrentAccount currentAccount) {
        AccountDTO accountDTO = AccountDTO.fromEntity(currentAccount);
        CurrentAccountDTO currentAccountDTO = (CurrentAccountDTO) accountDTO;
        currentAccountDTO.setOverdraft(currentAccount.getOverDraft());
        currentAccountDTO.setType(AccountTypeEnum.CURRENT_ACCOUNT);
        return currentAccountDTO;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrentAccountDTO that = (CurrentAccountDTO) o;
        return Double.compare(overdraft, that.overdraft) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overdraft);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' +
                super.toString() +
                "overdraft=" + overdraft +
                ')';
    }
}
