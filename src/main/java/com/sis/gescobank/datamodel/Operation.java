package com.sis.gescobank.datamodel;

import com.sis.gescobank.util.datamodel.LongEntity;
import com.sis.gescobank.util.generator.CodeGeneratorUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForEntity;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForValues;
import static java.util.Objects.isNull;

@Entity
@Table(indexes = {
        @Index(name = "ux_operation_operation_id", columnList = Operation.COLUMN_ID, unique = true),
        @Index(name = "ux_operation_code", columnList = Operation.COLUMN_CODE, unique = true),
        @Index(name = "fx_operation_account_id", columnList = Account.COLUMN_ID)
})
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = Operation.COLUMN_ID))})
public class Operation extends LongEntity {
    public static final String COLUMN_ID = "operationId";
    public static final String COLUMN_CODE = "code";

    public static final String PROPERTY_ACCOUNT = "account";

    public static final int LNG_CODE = 24;

    @Column(name = COLUMN_CODE, nullable = false, length = LNG_CODE)
    private String code;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private double amount;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Account.COLUMN_ID, nullable = false, foreignKey = @ForeignKey(name = "fk_operation_account_id"))
    private Account account;

    public Operation() {
        this.code = CodeGeneratorUtil.generateCode(LNG_CODE);
        this.createdAt = LocalDate.now();
    }

    public Operation(double amount, OperationTypeEnum type) {
        this();
        this.amount = amount;
        this.type = type;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationTypeEnum getType() {
        return type;
    }

    public void setType(OperationTypeEnum type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Operation create(double amount, OperationTypeEnum type) {
        return new Operation(amount, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Double.compare(amount, operation.amount) == 0 &&
                Objects.equals(super.getId(), operation.getId()) &&
                Objects.equals(code, operation.code) &&
                Objects.equals(description, operation.description) &&
                Objects.equals(createdAt, operation.createdAt) &&
                type == operation.type &&
                Objects.equals(account, operation.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(),
                code,
                createdAt,
                amount,
                description,
                type,
                account);
    }

    @Override
    public String toStringForLog() {
        return defaultToStringForEntity(this, this.getAccount().toStringForLog(), String.valueOf(this.getCode()));
    }

    @Override
    public String toStringForException() {
        return defaultToStringForValues(String.valueOf(this.getCode()));
    }
}
