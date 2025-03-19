package com.sis.gescobank.rest;


import com.sis.gescobank.dto.account.AccountDTO;
import com.sis.gescobank.dto.operation.OperationDTO;
import com.sis.gescobank.handler.ErrorResponse;
import com.sis.gescobank.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("accounts")
@Tag(name = "Accounts", description = "The accounts API")
public class AccountRest {

    private final AccountService accountService;

    public AccountRest(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Get all accounts", description = "Get all accounts", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class))))})
    public ResponseEntity<List<AccountDTO>> getCustomers() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{accountCode}")
    @Operation(summary = "Find an account", description = "Find an account by its code", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountCode) {
        return new ResponseEntity<>(accountService.getAccountByCode(accountCode), HttpStatus.OK);
    }

    @GetMapping("/{accountCode}/operations")
    @Operation(summary = "Get all operations of the account", description = "Get all operations of the account by its code", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OperationDTO.class))))})
    public ResponseEntity<List<OperationDTO>> getAccountHistory(@PathVariable String accountCode) {
        return new ResponseEntity<>(accountService.accountOperations(accountCode), HttpStatus.OK);
    }
}
