package dev.elshan.account.controller;

import dev.elshan.account.dto.CustomerDetailsDto;
import dev.elshan.account.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping("/{mobileNumber}")
    public CustomerDetailsDto getCustomerDetails(@PathVariable String mobileNumber) {
        return customerService.getCustomerDetails(mobileNumber);
    }

}
