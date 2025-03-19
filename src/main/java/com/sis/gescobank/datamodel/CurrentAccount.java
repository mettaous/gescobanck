package com.sis.gescobank.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

import static com.sis.gescobank.datamodel.CurrentAccount.TYPE_VALUE;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForEntity;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForValues;
import static java.util.Objects.isNull;

@Entity
@DiscriminatorValue(TYPE_VALUE)
public class CurrentAccount extends Account {
    public static final String TYPE_VALUE = "CURRENT_ACCOUNT";

    @Column(nullable = false)
    private double overDraft;

    public CurrentAccount() {
        super();
    }

    public CurrentAccount(double balance,
                          CurrencyEnum currency,
                          double overDraft) {
        super(balance, currency);
        this.overDraft = overDraft;
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrentAccount that = (CurrentAccount) o;
        return Double.compare(overDraft, that.overDraft) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overDraft);
    }

    @Override
    public String toStringForLog() {
        return defaultToStringForEntity(this,
                this.getCustomer().toStringForLog(),
                this.getCode(),
                String.valueOf(this.getOverDraft()));
    }

    @Override
    public String toStringForException() {
        return defaultToStringForValues(super.getCode(), String.valueOf(this.getOverDraft()));
    }
}