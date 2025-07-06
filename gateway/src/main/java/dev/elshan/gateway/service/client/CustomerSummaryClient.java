package dev.elshan.gateway.service.client;

import dev.elshan.gateway.dto.AccountsDto;
import dev.elshan.gateway.dto.CardsDto;
import dev.elshan.gateway.dto.CustomerDto;
import dev.elshan.gateway.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {

    @GetExchange(value= "/capital/customer/api/fetch", accept = "application/json")
    Mono<ResponseEntity<CustomerDto>> fetchCustomerDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value= "/capital/accounts/api/fetch", accept = "application/json")
    Mono<ResponseEntity<AccountsDto>> fetchAccountDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value= "/capital/loans/api/fetch", accept = "application/json")
    Mono<ResponseEntity<LoansDto>> fetchLoanDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value= "/capital/cards/api/fetch", accept = "application/json")
    Mono<ResponseEntity<CardsDto>> fetchCardDetails(@RequestParam("mobileNumber") String mobileNumber);

}
