package com.sis.gescobank.datarepository;

import com.sis.gescobank.datamodel.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> getByEmail(String email);
}
