package dev.elshan.card.query.projection;

import com.google.apps.card.v1.Card;
import dev.elshan.card.command.event.CardCreatedEvent;
import dev.elshan.card.command.event.CardDeletedEvent;
import dev.elshan.card.command.event.CardUpdatedEvent;
import dev.elshan.card.entity.Cards;
import dev.elshan.card.repository.CardsRepository;
import dev.elshan.card.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardProjection {
    private final CardsRepository cardsRepository;

    @EventHandler // will listen to event and store record in read database
    public void handleCreateEvent(CardCreatedEvent cardCreatedEvent){
        Cards entity = new Cards();
        BeanUtils.copyProperties(cardCreatedEvent, entity);
        cardsRepository.save(entity);
    }

    @EventHandler // will listen to event and update record in read database
    public void handleUpdateEvent(CardUpdatedEvent cardUpdatedEvent){
        Cards cards = cardsRepository.findByCardNumber(cardUpdatedEvent.getCardNumber()).get();
        cards.setAmountUsed(cardUpdatedEvent.getAmountUsed());
        cards.setAvailableAmount(cardUpdatedEvent.getAvailableAmount());
        cards.setTotalLimit(cardUpdatedEvent.getTotalLimit());
        cardsRepository.save(cards);
    }

    @EventHandler // will listen to event and store record in read database
    public void handleDeleteEvent(CardDeletedEvent cardDeletedEvent){
        Cards cards = cardsRepository.findByCardNumber(cardDeletedEvent.getCardNumber()).get();
        cardsRepository.delete(cards);
    }
}
