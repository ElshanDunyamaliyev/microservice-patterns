package dev.elshan.profile.events;

import lombok.Data;

@Data
public class CardDataChangedEvent {

    private String mobileNumber;
    private Long cardNumber;

}