package dev.elshan.account.service.impl;

import dev.elshan.account.dto.*;
import dev.elshan.account.exception.ResourceNotFoundException;
import dev.elshan.account.mapper.AccountsMapper;
import dev.elshan.account.mapper.CustomerMapper;
import dev.elshan.account.model.Accounts;
import dev.elshan.account.model.Customer;
import dev.elshan.account.repository.AccountsRepository;
import dev.elshan.account.repository.CustomerRepository;
import dev.elshan.account.service.ICustomerService;
import dev.elshan.account.service.client.CardClient;
import dev.elshan.account.service.client.LoanClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ICustomerServiceImpl implements ICustomerService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardClient cardClient;
    private final LoanClient loanClient;

    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        CardsDto cardsDto = cardClient.fetchCardDetails(mobileNumber);
        LoansDto loansDto = loanClient.fetchLoanDetails(mobileNumber);
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setName(customerDto.getName());
        customerDetailsDto.setEmail(customerDto.getEmail());
        customerDetailsDto.setMobileNumber(customerDto.getMobileNumber());
        customerDetailsDto.setAccountsDto(accountsDto);
        customerDetailsDto.setCardsDto(cardsDto);
        customerDetailsDto.setLoansDto(loansDto);
        return customerDetailsDto;
    }
}
