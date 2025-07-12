package dev.elshan.card.command.event;

import lombok.Data;

@Data
public class CardCreatedEvent {
    private String cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
}
