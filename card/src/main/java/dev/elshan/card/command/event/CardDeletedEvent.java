package dev.elshan.card.command.event;

import lombok.Data;

@Data
public class CardDeletedEvent {
    private String cardNumber;
}
