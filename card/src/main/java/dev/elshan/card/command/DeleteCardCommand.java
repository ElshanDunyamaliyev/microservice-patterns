package dev.elshan.card.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteCardCommand {

    @TargetAggregateIdentifier
    private String cardNumber;
}
