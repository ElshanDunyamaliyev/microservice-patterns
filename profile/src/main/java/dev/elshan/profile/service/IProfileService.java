package dev.elshan.profile.service;


import dev.elshan.profile.dto.ProfileDto;
import dev.elshan.profile.entity.Profile;

public interface IProfileService {

//    /**
//     * @param customerDataChangedEvent - CustomerDataChangedEvent Object
//     */
//    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);
//
//    /**
//     * @param accountDataChangedEvent - AccountDataChangedEvent Object
//     */
//    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);
//
//    /**
//     * @param loanDataChangedEvent - LoanDataChangedEvent Object
//     */
//    void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent);
//
//    /**
//     * @param customerDataChangedEvent - CardDataChangedEvent Object
//     */
//    void handleCardDataChangedEvent(CardDataChangedEvent customerDataChangedEvent);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Profile Details based on a given mobileNumber
     */
    ProfileDto fetchProfile(String mobileNumber);
}
