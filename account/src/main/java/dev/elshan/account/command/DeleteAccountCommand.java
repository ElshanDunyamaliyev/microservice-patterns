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
public class DeleteAccountCommand {
    @TargetAggregateIdentifier
    private Long accountNumber;
    private String mobileNumber;
}
