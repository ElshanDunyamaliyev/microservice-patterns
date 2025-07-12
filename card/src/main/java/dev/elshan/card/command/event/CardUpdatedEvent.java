package dev.elshan.card.command.event;

import lombok.Data;

@Data
public class CardUpdatedEvent {
    private String cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
}
