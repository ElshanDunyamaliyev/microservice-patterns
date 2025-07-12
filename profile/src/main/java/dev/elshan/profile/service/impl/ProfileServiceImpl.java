package dev.elshan.profile.service.impl;

import dev.elshan.profile.dto.ProfileDto;
import dev.elshan.profile.entity.Profile;
import dev.elshan.profile.exception.ResourceNotFoundException;
import dev.elshan.profile.mapper.ProfileMapper;
import dev.elshan.profile.repository.ProfileRepository;
import dev.elshan.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private ProfileRepository profileRepository;


    @Override
    public ProfileDto fetchProfile(String mobileNumber) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Profile", "mobileNumber", mobileNumber)
        );
        ProfileDto profileDto = ProfileMapper.mapToProfileDto(profile, new ProfileDto());
        return profileDto;
    }
}
