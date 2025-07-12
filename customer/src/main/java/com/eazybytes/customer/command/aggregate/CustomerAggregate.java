package com.eazybytes.customer.command.aggregate;

import com.eazybytes.customer.command.CreateCustomerCommand;
import com.eazybytes.customer.command.DeleteCustomerCommand;
import com.eazybytes.customer.command.UpdateCustomerCommand;
import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import dev.elshan.common.CustomerDataChangedEvent;


import java.util.List;

@Slf4j
@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;

    public CustomerAggregate() {
    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command, CustomerRepository customerRepository) {
        // validation logic will come from customer command interceptor
//        Optional<Customer> optional = customerRepository.findByMobileNumberAndActiveSw(command.getMobileNumber(), CustomerConstants.ACTIVE_SW);
//        if (optional.isPresent())
//            throw new CustomerAlreadyExistsException("Customer is exist with given mobile number : " + command.getMobileNumber());

        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(command, customerCreatedEvent);
        log.info("{}",customerCreatedEvent);
        AggregateLifecycle.apply(customerCreatedEvent);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(command, customerDataChangedEvent);
        log.info("{}",customerDataChangedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        this.customerId = customerCreatedEvent.getCustomerId();
        this.name = customerCreatedEvent.getName();
        this.email = customerCreatedEvent.getEmail();
        this.mobileNumber = customerCreatedEvent.getMobileNumber();
        this.activeSw = customerCreatedEvent.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand command, EventStore eventStore) {
        List<?> events = eventStore.readEvents(command.getCustomerId()).asStream().toList();
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(command, customerUpdatedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(command, customerDataChangedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent){
        this.name = customerUpdatedEvent.getName();
        this.email = customerUpdatedEvent.getEmail();
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand command, CustomerRepository customerRepository) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(command, customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(command, customerDataChangedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent customerDeletedEvent){
        this.activeSw = customerDeletedEvent.isActiveSw();
    }
}

