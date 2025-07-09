package dev.elshan.account.command.controller;

import dev.elshan.account.command.CreateAccountCommand;
import dev.elshan.account.command.DeleteAccountCommand;
import dev.elshan.account.command.UpdateAccountCommand;
import dev.elshan.account.constants.AccountsConstants;
import dev.elshan.account.dto.AccountsDto;
import dev.elshan.account.dto.CustomerDto;
import dev.elshan.account.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountsDto accountsDto) {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        BeanUtils.copyProperties(accountsDto, createAccountCommand);
        commandGateway.sendAndWait(createAccountCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody AccountsDto accountsDto) {
        UpdateAccountCommand updateAccountCommand = new UpdateAccountCommand();
        BeanUtils.copyProperties(accountsDto, updateAccountCommand);
        commandGateway.sendAndWait(updateAccountCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber, Long accountNumber) {
        DeleteAccountCommand deleteAccountCommand = DeleteAccountCommand.builder()
                .accountNumber(accountNumber)
                .mobileNumber(mobileNumber)
                .build();
        commandGateway.sendAndWait(deleteAccountCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }
}
