package com.eazybytes.customer.query.projection;

import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.entity.Customer;
import com.eazybytes.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProjection {

    private final ICustomerService customerService;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        Customer entity = new Customer();
        BeanUtils.copyProperties(customerCreatedEvent, entity);
        customerService.createCustomer(entity);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent){
        customerService.updateCustomer(customerUpdatedEvent);
    }

    @EventHandler
    public void on(CustomerDeletedEvent customerDeletedEvent){
        customerService.deleteCustomer(customerDeletedEvent.getCustomerId());
    }
}
