package com.sis.gescobank.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForEntity;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForValues;
import static java.util.Objects.isNull;

@Entity
@DiscriminatorValue(SavingAccount.TYPE_VALUE)
public class SavingAccount extends Account {

    public static final String TYPE_VALUE = "SAVING_ACCOUNT";

    @Column(nullable = false)
    private double interestRate;

    public SavingAccount() {
        super();
    }

    public SavingAccount(double balance,
                         CurrencyEnum currency,
                         double interestRate) {
        super(balance, currency);
        this.interestRate = interestRate;
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
        SavingAccount that = (SavingAccount) o;
        return Double.compare(interestRate, that.interestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate);
    }

    @Override
    public String toStringForLog() {
        return defaultToStringForEntity(this,
                this.getCustomer().toStringForLog(),
                this.getCode(),
                String.valueOf(this.getInterestRate()));
    }

    @Override
    public String toStringForException() {
        return defaultToStringForValues(super.getCode(), String.valueOf(this.getInterestRate()));
    }
}
