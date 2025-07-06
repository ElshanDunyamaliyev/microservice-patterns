package com.eazybytes.customer.query;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class FindCustomerQuery {
    private final String mobileNumber;
}
