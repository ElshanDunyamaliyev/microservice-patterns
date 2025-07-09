package dev.elshan.account.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountCommand {
    private String name;
    private String email;
    private String mobileNumber;
    @TargetAggregateIdentifier
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
