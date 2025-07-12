package dev.elshan.card.query.handler;

import dev.elshan.card.dto.CardsDto;
import dev.elshan.card.query.FindCardQuery;
import dev.elshan.card.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CardQueryHandler {
    private final ICardsService cardsService;

    @QueryHandler
    public CardsDto getCard(FindCardQuery findCardQuery){
        return cardsService.fetchCard(findCardQuery.getMobileNumber());
    }
}
