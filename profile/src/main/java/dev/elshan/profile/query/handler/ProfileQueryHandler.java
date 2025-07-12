package dev.elshan.profile.query.handler;

import dev.elshan.profile.dto.ProfileDto;
import dev.elshan.profile.query.FindProfileQuery;
import dev.elshan.profile.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileQueryHandler {
    private final IProfileService iProfileService;

    @QueryHandler
    public ProfileDto findCustomer(FindProfileQuery findProfileQuery) {
        return iProfileService.fetchProfile(findProfileQuery.getMobileNumber());
    }
}
