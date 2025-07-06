package dev.elshan.account.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDetailsDto{
    String name;
    String email;
    String mobileNumber;
    AccountsDto accountsDto;
    CardsDto cardsDto;
    LoansDto loansDto;
}
