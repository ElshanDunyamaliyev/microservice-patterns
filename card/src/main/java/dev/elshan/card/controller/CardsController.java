package dev.elshan.card.controller;

import dev.elshan.card.constants.CardsConstants;
import dev.elshan.card.dto.CardsDto;
import dev.elshan.card.dto.CardsInfoDto;
import dev.elshan.card.dto.ErrorResponseDto;
import dev.elshan.card.dto.ResponseDto;
import dev.elshan.card.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eazy Bytes
 */

//@Tag(
//        name = "CRUD REST APIs for Cards in EazyBank",
//        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
//)
//@RestController
//@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@RequiredArgsConstructor
//@Validated
//public class CardsController {
//
//    private final ICardsService iCardsService;
//
//    private final CardsInfoDto cardsInfoDto;
//
//    @GetMapping("/cards-contact-info")
//    public CardsInfoDto getAccountsContactInfo() {
//        return cardsInfoDto;
//    }
//
//}
