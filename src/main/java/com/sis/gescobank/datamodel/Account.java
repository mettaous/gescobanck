package com.sis.gescobank.datamodel;

import com.sis.gescobank.util.datamodel.LongEntity;
import com.sis.gescobank.util.generator.CodeGeneratorUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sis.gescobank.datamodel.Account.COLUMN_TYPE;
import static com.sis.gescobank.datamodel.Account.LNG_TYPE;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForEntity;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForValues;
import static java.util.Objects.isNull;

@Entity
@Table(indexes = {
        @Index(name = "ux_account_account_id", columnList = Account.COLUMN_ID, unique = true),
        @Index(name = "ux_account_code", columnList = Account.COLUMN_CODE, unique = true),
        @Index(name = "fx_account_customer_id", columnList = Customer.COLUMN_ID)
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = COLUMN_TYPE, length = LNG_TYPE, discriminatorType = DiscriminatorType.STRING)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = Account.COLUMN_ID))})
public abstract class Account extends LongEntity {

    public static final String COLUMN_ID = "accountId";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CODE = "code";

    public static final String PROPERTY_CODE = "code";
    public static final String PROPERTY_CUSTOMER = "customer";
    public static final String PROPERTY_CURRENCY = "currency";

    public static final int LNG_TYPE = 15;
    public static final int LNG_CODE = 24;

    @Column(name = COLUMN_CODE, nullable = false, length = LNG_CODE)
    private String code;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyEnum currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Customer.COLUMN_ID, nullable = false, foreignKey = @ForeignKey(name = "fk_account_customer_id"))
    private Customer customer;

    @OneToMany(mappedBy = Operation.PROPERTY_ACCOUNT, fetch = FetchType.LAZY)
    private List<Operation> operations = new ArrayList<>();

    protected Account() {
        this.createdAt = LocalDate.now();
        this.status = AccountStatusEnum.CREATED;
        this.code = CodeGeneratorUtil.generateCode(LNG_CODE);
    }

    protected Account(double balance, CurrencyEnum currency) {
        this();
        this.balance = balance;
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0 &&
                Objects.equals(this.getId(), account.getId()) &&
                Objects.equals(code, account.code) &&
                Objects.equals(createdAt, account.createdAt) &&
                status == account.status &&
                currency == account.currency &&
                Objects.equals(customer, account.customer) &&
                Objects.equals(operations, account.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),
                code,
                balance,
                createdAt,
                status,
                currency,
                customer,
                operations);
    }

    @Override
    public String toStringForLog() {
        return defaultToStringForEntity(this, this.getCustomer().toStringForLog(), this.getCode());
    }

    @Override
    public String toStringForException() {
        return defaultToStringForValues(this.getCode());
    }
}
