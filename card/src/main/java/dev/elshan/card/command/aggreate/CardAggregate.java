package dev.elshan.card.command.aggreate;

import dev.elshan.card.command.CreateCardCommand;
import dev.elshan.card.command.DeleteCardCommand;
import dev.elshan.card.command.UpdateCardCommand;
import dev.elshan.card.command.event.CardCreatedEvent;
import dev.elshan.card.command.event.CardDeletedEvent;
import dev.elshan.card.command.event.CardUpdatedEvent;
import dev.elshan.card.entity.Cards;
import dev.elshan.card.repository.CardsRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Aggregate // This class is responsible for multiple things
// 1. We write command gateway sendAndWait in controller. We will handle business logic in this class
// 2. This class is responsible for saving event in db
// 3. Also after saving events this class is also responsible to publish events to event bus
public class CardAggregate {

    @AggregateIdentifier // aggregate identifier annotation
    // tells axon framework that loads existing record
    // from db and when updating or deleting, write new records sequentially
    private String cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private boolean active;

    public CardAggregate() { // axon framework needs an empty constructor

    }

    @CommandHandler
    public CardAggregate(CreateCardCommand createCardCommand, CardsRepository cardsRepository) {

        Optional<Cards> optionalCard = cardsRepository.findByMobileNumber(createCardCommand.getMobileNumber());
        if (optionalCard.isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Card is already exist with given mobile number : " + optionalCard.get().getMobileNumber());
        }
        CardCreatedEvent cardCreatedEvent = new CardCreatedEvent();
        BeanUtils.copyProperties(createCardCommand, cardCreatedEvent);
        AggregateLifecycle.apply(cardCreatedEvent); // publishing event
    }

    @EventSourcingHandler // storing event in event sourcing database
    public void on(CardCreatedEvent cardCreatedEvent) {
        this.amountUsed = cardCreatedEvent.getAmountUsed();
        this.availableAmount = cardCreatedEvent.getAvailableAmount();
        this.cardNumber = cardCreatedEvent.getCardNumber();
        this.cardType = cardCreatedEvent.getCardType();
        this.mobileNumber = cardCreatedEvent.getMobileNumber();
        this.totalLimit = cardCreatedEvent.getTotalLimit();
        this.active = true;
    }

    @CommandHandler
    public void handleUpdateCommand(UpdateCardCommand updateCardCommand, CardsRepository cardsRepository) {
        Optional<Cards> optionalCard = cardsRepository.findByCardNumber(updateCardCommand.getCardNumber());
        if (optionalCard.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Card is not found with given card number : " + updateCardCommand.getCardNumber());
        }

        CardUpdatedEvent cardUpdatedEvent = new CardUpdatedEvent();

        BeanUtils.copyProperties(updateCardCommand, cardUpdatedEvent);

        AggregateLifecycle.apply(cardUpdatedEvent);
    }

    @EventSourcingHandler // storing event in event sourcing database
    public void on(CardUpdatedEvent cardUpdatedEvent) {
        // Other fields will come from previous events
        this.amountUsed = cardUpdatedEvent.getAmountUsed();
        this.availableAmount = cardUpdatedEvent.getTotalLimit() - cardUpdatedEvent.getAmountUsed();
        this.totalLimit = cardUpdatedEvent.getTotalLimit();
    }

    @CommandHandler
    public void handleDeleteCommand(DeleteCardCommand deleteCardCommand, CardsRepository cardsRepository) {
        Optional<Cards> optionalCard = cardsRepository.findByMobileNumber(deleteCardCommand.getCardNumber());
        if (optionalCard.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Card is not found with given card number : " + deleteCardCommand.getCardNumber());
        }

        CardDeletedEvent cardDeletedEvent = new CardDeletedEvent();

        BeanUtils.copyProperties(deleteCardCommand, cardDeletedEvent);

        AggregateLifecycle.apply(cardDeletedEvent);
    }

    @EventSourcingHandler // storing event in event sourcing database
    public void on(CardDeletedEvent cardDeletedEvent) {
        this.active = false;
    }

}
