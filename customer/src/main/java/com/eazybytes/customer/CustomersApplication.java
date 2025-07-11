package com.eazybytes.customer;

import com.eazybytes.customer.command.interceptor.CustomerCommandInterceptor;
import com.eazybytes.customer.config.AxonConfig;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@Import(AxonConfig.class)
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Autowired
    public void registerCustomerCommandInterceptor(ApplicationContext applicationContext, CommandGateway commandGateway){
        commandGateway.registerDispatchInterceptor(applicationContext.getBean(CustomerCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer config){
        config.registerListenerInvocationErrorHandler("customer-group", configuration -> PropagatingErrorHandler.instance());
    }

}
