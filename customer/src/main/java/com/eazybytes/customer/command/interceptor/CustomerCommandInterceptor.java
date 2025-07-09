package com.eazybytes.customer.command.interceptor;

import com.eazybytes.customer.command.CreateCustomerCommand;
import com.eazybytes.customer.command.DeleteCustomerCommand;
import com.eazybytes.customer.command.UpdateCustomerCommand;
import com.eazybytes.customer.constants.CustomerConstants;
import com.eazybytes.customer.entity.Customer;
import com.eazybytes.customer.exception.CustomerAlreadyExistsException;
import com.eazybytes.customer.exception.ResourceNotFoundException;
import com.eazybytes.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final CustomerRepository customerRepository;


    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(command.getPayloadType().equals(CreateCustomerCommand.class)){
                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> optional = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW);
                if (optional.isPresent())
                    throw new CustomerAlreadyExistsException("Customer is exist with given mobile number : " + createCustomerCommand.getMobileNumber());
            }else if(command.getPayloadType().equals(UpdateCustomerCommand.class)){
                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
                customerRepository.findByMobileNumberAndActiveSw(updateCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW).orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerCommand.getMobileNumber()));
            }else if(command.getPayloadType().equals(DeleteCustomerCommand.class)){
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                customerRepository.findByCustomerIdAndActiveSw(deleteCustomerCommand.getCustomerId(),CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,("Customer not found with given id " + deleteCustomerCommand.getCustomerId())));
            }
            return command;
        };
    }
}
