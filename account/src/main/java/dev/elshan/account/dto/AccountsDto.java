package dev.elshan.account.dto;

import lombok.Data;

@Data
public class AccountsDto {
    private String name;
    private String email;
    private String mobileNumber;
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}