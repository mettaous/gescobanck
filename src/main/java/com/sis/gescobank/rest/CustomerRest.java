package com.sis.gescobank.rest;

import com.sis.gescobank.dto.customer.CustomerDTO;
import com.sis.gescobank.handler.ErrorResponse;
import com.sis.gescobank.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("customers")
@Tag(name = "Customer", description = "The customer API")
public class CustomerRest {

    private final CustomerService customerService;

    public CustomerRest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Get all customers", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create or update a customer", description = "Create or update a customer", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        HttpStatus httpStatus = isNull(customerDTO.getId()) ? HttpStatus.CREATED : HttpStatus.OK;
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO), httpStatus);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Find a customer", description = "Find customer by its identifier", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a customer", description = "Delete a customer by its identifier")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok().build();
    }
}
