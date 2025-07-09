package dev.elshan.account.command.event;

import lombok.Data;
import lombok.Value;

@Data
public class AccountUpdatedEvent {
    private String name;
    private String email;
    private String mobileNumber;
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
