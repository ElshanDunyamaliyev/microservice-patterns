package dev.elshan.account.service.client;

import dev.elshan.account.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans")
public interface LoanClient {
    @GetMapping("/api/fetch")
    LoansDto fetchLoanDetails(@RequestParam String mobileNumber);
}
