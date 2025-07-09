package dev.elshan.account.command.interceptor;

import dev.elshan.account.command.CreateAccountCommand;
import dev.elshan.account.command.DeleteAccountCommand;
import dev.elshan.account.command.UpdateAccountCommand;
import dev.elshan.account.exception.ResourceNotFoundException;
import dev.elshan.account.model.Accounts;
import dev.elshan.account.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AccountCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final AccountsRepository accountsRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(command.getPayloadType().equals(CreateAccountCommand.class)){
                CreateAccountCommand createAccountCommand = (CreateAccountCommand) command.getPayload();
                Optional<Accounts> optional = accountsRepository.findByAccountNumber(createAccountCommand.getAccountNumber());
                if (optional.isPresent())
                    throw new ResponseStatusException(BAD_REQUEST,"Account is exist with given mobile number : " + createAccountCommand.getMobileNumber());
            }else if(command.getPayloadType().equals(UpdateAccountCommand.class)){
                UpdateAccountCommand updateAccountCommand = (UpdateAccountCommand) command.getPayload();
                accountsRepository.findByAccountNumber(updateAccountCommand.getAccountNumber()).orElseThrow(() ->
                        new ResourceNotFoundException("Account", "mobileNumber", updateAccountCommand.getMobileNumber()));
            }else if(command.getPayloadType().equals(DeleteAccountCommand.class)){
                DeleteAccountCommand deleteAccountCommand = (DeleteAccountCommand) command.getPayload();
                accountsRepository.findByAccountNumber(deleteAccountCommand.getAccountNumber())
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,("Account not found with given id " + deleteAccountCommand.getMobileNumber())));
            }
            return command;
        };
    }
}
