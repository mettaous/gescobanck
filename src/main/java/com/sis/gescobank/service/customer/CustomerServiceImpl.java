package com.sis.gescobank.service.customer;

import com.sis.gescobank.datamodel.Customer;
import com.sis.gescobank.datarepository.CustomerRepository;
import com.sis.gescobank.dto.customer.CustomerDTO;
import com.sis.gescobank.exception.DataAlreadyExistBusinessException;
import com.sis.gescobank.exception.DataNotFoundBusinessException;
import com.sis.gescobank.exception.ValidationBusinessException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundBusinessException(Customer.class.getSimpleName(),
                        String.valueOf(customerId)));
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        return CustomerDTO.fromEntity(findCustomerById(customerId));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        checkCustomerDataValidity(customerDTO);
        Customer customer;
        if (isNull(customerDTO.getId())) {
            customer = createCustomer(customerDTO);
        } else {
            customer = updateCustomer(customerDTO);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Save customer : {}", customer.toStringForLog());
        }
        return CustomerDTO.fromEntity(customer);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDTO::fromEntity)
                .toList();
    }

    private void checkCustomerDataValidity(CustomerDTO customerDTO) {
        checkFieldRequired(customerDTO.getEmail(), Customer.PROPERTY_EMAIL);
        checkFieldRequired(customerDTO.getFirstName(), Customer.PROPERTY_FIRST_NAME);
        checkFieldRequired(customerDTO.getLastName(), Customer.PROPERTY_LAST_NAME);

        checkFieldStringLength(customerDTO.getEmail(), Customer.LNG_EMAIL, Customer.PROPERTY_EMAIL);
        checkFieldStringLength(customerDTO.getFirstName(), Customer.LNG_FIRST_NAME, Customer.PROPERTY_FIRST_NAME);
        checkFieldStringLength(customerDTO.getLastName(), Customer.LNG_LAST_NAME, Customer.PROPERTY_LAST_NAME);
        checkFieldStringLength(customerDTO.getPhone(), Customer.LNG_PHONE, Customer.PROPERTY_PHONE);
    }

    private Customer createCustomer(CustomerDTO customerDTO) {
        checkCustomerNonExistence(customerDTO.getEmail());
        if (logger.isInfoEnabled()) {
            logger.info("Creating new customer...");
        }
        Customer customer = Customer.create(customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getEmail());
        updateCustomerData(customerDTO, customer);
        customer = customerRepository.save(customer);
        if (logger.isInfoEnabled()) {
            logger.info("New customer created: {}", customer.toStringForLog());
        }
        return customer;
    }

    private Customer updateCustomer(CustomerDTO customerDTO) {
        Customer customer = findCustomerById(customerDTO.getId());
        if (logger.isInfoEnabled()) {
            logger.info("Updating existing customer with id: {}", customerDTO.getId());
        }
        updateCustomerData(customerDTO, customer);
        return customer;
    }

    private void updateCustomerData(CustomerDTO customerDTO, Customer customer) {
        customer.setPhone(customerDTO.getPhone());
    }

    private void existsByEmail(String customerEmail) {
        Optional<Customer> customer = customerRepository.getByEmail(customerEmail);
        if (customer.isPresent()) {
            throw new DataAlreadyExistBusinessException(Customer.class.getSimpleName(),
                    Collections.singletonMap(Customer.PROPERTY_EMAIL, customerEmail));
        }
    }

    private void checkFieldRequired(String value, String fieldName) {
        if (isNull(value)) {
            throw new ValidationBusinessException(fieldName + " is required");
        }
    }

    private void checkFieldStringLength(String value, int maxLength, String fieldName) {
        if (isNull(value)) {
            return;
        }

        if (value.isBlank()) {
            throw new ValidationBusinessException(fieldName + " is blank");
        }

        if (value.length() > maxLength) {
            throw new ValidationBusinessException(
                    String.format("The size of the field '%s' is too large: %d exceeds the maximum allowed %d",
                            fieldName, value.length(), maxLength)
            );
        }
    }

    private void checkCustomerNonExistence(String customerEmail) {
        existsByEmail(customerEmail);
    }
}
