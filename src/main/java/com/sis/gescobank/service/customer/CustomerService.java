package com.sis.gescobank.service.customer;

import com.sis.gescobank.datamodel.Customer;
import com.sis.gescobank.dto.customer.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Long customerId);

    CustomerDTO getCustomerById(Long customerId);

    CustomerDTO saveCustomer(CustomerDTO customerDto);

    void deleteCustomerById(Long customerId);

    List<CustomerDTO> getCustomers();
}
