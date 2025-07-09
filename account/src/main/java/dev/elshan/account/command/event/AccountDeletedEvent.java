package dev.elshan.account.command.event;

import lombok.Data;
import lombok.Value;

@Data
public class AccountDeletedEvent {
    private String mobileNumber;
    private Long accountNumber;
}
