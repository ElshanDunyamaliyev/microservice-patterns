package dev.elshan.common;

import lombok.Data;

@Data
public class AccountDataChangedEvent {

    private String mobileNumber;
    private Long accountNumber;

}
