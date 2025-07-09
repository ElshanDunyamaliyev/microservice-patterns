package dev.elshan.account.query.handler;

import dev.elshan.account.dto.AccountsDto;
import dev.elshan.account.model.Accounts;
import dev.elshan.account.query.FindAccountQuery;
import dev.elshan.account.repository.AccountsRepository;
import dev.elshan.account.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AccountQueryHandler {
    private final AccountsRepository accountsRepository;

    @QueryHandler
    public AccountsDto fetchAccount(FindAccountQuery findAccountQuery){
        AccountsDto accountsDto = new AccountsDto();
        Accounts accounts = accountsRepository.findByAccountNumber(findAccountQuery.getAccountNumber()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "account not found"));
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accounts.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }
}
