package dev.elshan.account.service.client;

import dev.elshan.account.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards")
public interface CardClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    CardsDto fetchCardDetails(@RequestParam String mobileNumber);
}
