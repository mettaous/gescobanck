package com.sis.gescobank.datamodel;

import com.sis.gescobank.util.datamodel.LongEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForEntity;
import static com.sis.gescobank.util.datamodel.UtilDataModel.defaultToStringForValues;
import static java.util.Objects.isNull;

@Entity
@Table(indexes = {
        @Index(name = "ux_customer_customer_id", columnList = Customer.COLUMN_EMAIL, unique = true),
        @Index(name = "ux_customer_email", columnList = Customer.COLUMN_ID, unique = true)
})
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = Customer.COLUMN_ID))})
public class Customer extends LongEntity {

    public static final String COLUMN_ID = "customerId";
    public static final String COLUMN_EMAIL = "email";

    public static final int LNG_EMAIL = 30;
    public static final int LNG_PHONE = 20;
    public static final int LNG_FIRST_NAME = 20;
    public static final int LNG_LAST_NAME = 20;

    public static final String PROPERTY_EMAIL = "email";
    public static final String PROPERTY_FIRST_NAME = "first name";
    public static final String PROPERTY_LAST_NAME = "last name";
    public static final String PROPERTY_PHONE = "phone";

    @Column(nullable = false, length = LNG_FIRST_NAME)
    private String firstName;

    @Column(nullable = false, length = LNG_LAST_NAME)
    private String lastName;

    @Column(name = COLUMN_EMAIL, length = LNG_EMAIL, nullable = false)
    private String email;

    @Column(length = LNG_PHONE)
    private String phone;

    @OneToMany(mappedBy = Account.PROPERTY_CUSTOMER, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public Customer() {

    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public static Customer create(String firstName, String lastName, String email) {
        return new Customer(firstName, lastName, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(this.getId(), customer.getId()) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(accounts, customer.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),
                firstName,
                lastName,
                email,
                phone,
                accounts);
    }

    @Override
    public String toStringForLog() {
        return defaultToStringForEntity(this, this.getEmail());
    }

    @Override
    public String toStringForException() {
        return defaultToStringForValues(this.getEmail());
    }
}
