package dev.elshan.card.command.controller;

import dev.elshan.card.command.CreateCardCommand;
import dev.elshan.card.command.DeleteCardCommand;
import dev.elshan.card.command.UpdateCardCommand;
import dev.elshan.card.constants.CardsConstants;
import dev.elshan.card.dto.CardsDto;
import dev.elshan.card.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CardAggregateController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(new Random().nextInt(0, 9));
        }
        CreateCardCommand createCardCommand = CreateCardCommand.builder()
                .mobileNumber(mobileNumber)
                .cardNumber(stringBuilder.toString())
                .cardType("Debit")
                .amountUsed(0)
                .totalLimit(10000)
                .availableAmount(10000)
                .build();

        commandGateway.sendAndWait(createCardCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {

        UpdateCardCommand updateCardCommand = UpdateCardCommand.builder()
                .totalLimit(cardsDto.getTotalLimit())
                .cardNumber(cardsDto.getCardNumber())
                .amountUsed(cardsDto.getAmountUsed())
                .availableAmount(cardsDto.getAvailableAmount())
                .mobileNumber(cardsDto.getMobileNumber())
                .cardType(cardsDto.getCardType())
                .build();

        commandGateway.sendAndWait(updateCardCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                         String cardNumber) {
        DeleteCardCommand deleteCardCommand = DeleteCardCommand.builder()
                .cardNumber(cardNumber)
                .build();

        commandGateway.sendAndWait(deleteCardCommand); // with this command gateway send and wait
        // we are telling axon framework
        // to handle business logic when new command is came (We are doing it in aggregate class)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));

    }
}
