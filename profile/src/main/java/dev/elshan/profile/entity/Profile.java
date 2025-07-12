package dev.elshan.profile.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "active_sw")
    private boolean activeSw = false;

    @Column(name = "account_number")
    private long accountNumber;

    @Column(name = "card_number")
    private long cardNumber;

    @Column(name = "loan_number")
    private long loanNumber;
}
