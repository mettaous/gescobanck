package com.sis.gescobank.dto.operation;

import com.sis.gescobank.datamodel.Operation;
import com.sis.gescobank.datamodel.OperationTypeEnum;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.isNull;

public class OperationDTO {

    private String code;
    private LocalDate createdAt;
    private double amount;
    private String description;
    private OperationTypeEnum type;

    public OperationDTO() {
    }

    public static OperationDTO fromEntity(Operation operation) {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setCode(operation.getCode());
        operationDTO.setCreatedAt(operation.getCreatedAt());
        operationDTO.setAmount(operation.getAmount());
        operationDTO.setDescription(operation.getDescription());
        operationDTO.setType(operation.getType());
        return operationDTO;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        OperationDTO that = (OperationDTO) o;
        return Double.compare(amount, that.amount) == 0 &&
                Objects.equals(code, that.code) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(description, that.description) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, createdAt, amount, description, type);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' +
                "code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", type=" + type +
                ')';
    }
}
