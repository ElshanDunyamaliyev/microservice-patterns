package dev.elshan.profile.query.projection;


import dev.elshan.common.AccountDataChangedEvent;
import dev.elshan.common.CustomerDataChangedEvent;
import dev.elshan.profile.constants.ProfileConstants;
import dev.elshan.profile.entity.Profile;
import dev.elshan.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("customer-group")
public class ProfileProjection {

    private final ProfileRepository profileRepository;

    @EventHandler
    public void on(AccountDataChangedEvent accountDataChangedEvent) {
        log.info("salam");
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(accountDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW).get();
        profile.setAccountNumber(accountDataChangedEvent.getAccountNumber());
        profileRepository.save(profile);
    }

    @EventHandler
    public void on(CustomerDataChangedEvent customerDataChangedEvent) {
        log.info("sagol");
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(customerDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW).orElseGet(Profile::new);
        profile.setName(customerDataChangedEvent.getName());
        profile.setActiveSw(true);
        profile.setMobileNumber(customerDataChangedEvent.getMobileNumber());
        profileRepository.save(profile);
    }


}
