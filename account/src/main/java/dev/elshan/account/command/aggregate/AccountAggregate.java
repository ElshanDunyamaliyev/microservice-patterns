package dev.elshan.account.command.aggregate;

import dev.elshan.account.command.CreateAccountCommand;
import dev.elshan.account.command.DeleteAccountCommand;
import dev.elshan.account.command.UpdateAccountCommand;
import dev.elshan.account.command.event.AccountCreatedEvent;
import dev.elshan.account.command.event.AccountDeletedEvent;
import dev.elshan.account.command.event.AccountUpdatedEvent;
import dev.elshan.common.AccountDataChangedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private Long accountNumber;
    private String name;
    private String email;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
        BeanUtils.copyProperties(command, accountCreatedEvent);
        AggregateLifecycle.apply(accountCreatedEvent);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(command, accountDataChangedEvent);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.name = event.getName();
        this.email = event.getEmail();
        this.mobileNumber = event.getMobileNumber();
        this.accountNumber = event.getAccountNumber();
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
    }

    @CommandHandler
    public void update(UpdateAccountCommand command) {
        AccountUpdatedEvent accountUpdatedEvent = new AccountUpdatedEvent();
        BeanUtils.copyProperties(command, accountUpdatedEvent);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(command, accountDataChangedEvent);
        AggregateLifecycle.apply(accountUpdatedEvent);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent event) {
        this.name = event.getName();
        this.email = event.getEmail();
        this.mobileNumber = event.getMobileNumber();
        this.accountNumber = event.getAccountNumber();
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
    }

    @CommandHandler
    public void delete(DeleteAccountCommand command) {
        AccountDeletedEvent accountDeletedEvent = new AccountDeletedEvent();
        BeanUtils.copyProperties(command, accountDeletedEvent);
        AggregateLifecycle.apply(accountDeletedEvent);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(command, accountDataChangedEvent);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent event) {
        this.mobileNumber = event.getMobileNumber();
        this.accountNumber = event.getAccountNumber();
    }


}
