package dev.elshan.account.query.controller;

import dev.elshan.account.dto.AccountsContactInfoDto;
import dev.elshan.account.dto.AccountsDto;
import dev.elshan.account.query.FindAccountQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class AccountQueryController {
    private final QueryGateway queryGateway;

    @GetMapping("/accounts")
    public AccountsDto getAccountsContactInfo(@RequestParam("accountNumber") Long accountNumber) {
        FindAccountQuery findAccountQuery = new FindAccountQuery(accountNumber);
        AccountsDto accountsDto = queryGateway.query(findAccountQuery, AccountsDto.class).join();
        return accountsDto;
    }
}
