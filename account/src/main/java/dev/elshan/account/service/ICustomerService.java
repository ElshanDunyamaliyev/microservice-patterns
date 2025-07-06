package dev.elshan.account.service;

import dev.elshan.account.dto.CustomerDetailsDto;

public interface ICustomerService {
    CustomerDetailsDto getCustomerDetails(String mobileNumber);
}
