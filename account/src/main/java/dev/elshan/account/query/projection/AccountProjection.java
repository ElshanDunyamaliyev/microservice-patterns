package dev.elshan.account.query.projection;


import dev.elshan.account.command.event.AccountCreatedEvent;
import dev.elshan.account.command.event.AccountDeletedEvent;
import dev.elshan.account.command.event.AccountUpdatedEvent;
import dev.elshan.account.model.Accounts;
import dev.elshan.account.repository.AccountsRepository;
import dev.elshan.account.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountProjection {
    private final AccountsRepository accountsRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        Accounts accounts = new Accounts();
        BeanUtils.copyProperties(event,accounts);
        accountsRepository.save(accounts);
    }

    @EventHandler
    public void on(AccountUpdatedEvent event){
        Accounts accounts = accountsRepository.findByAccountNumber(event.getAccountNumber()).orElseThrow();
        accounts.setAccountType(event.getAccountType());
        accounts.setBranchAddress(event.getBranchAddress());
        accountsRepository.save(accounts);
    }

    @EventHandler
    public void on(AccountDeletedEvent event){
        Accounts accounts = accountsRepository.findByAccountNumber(event.getAccountNumber()).orElseThrow();
        accountsRepository.delete(accounts);
    }
}
