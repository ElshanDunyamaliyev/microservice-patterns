package dev.elshan.profile.mapper;


import dev.elshan.profile.dto.ProfileDto;
import dev.elshan.profile.entity.Profile;

public class ProfileMapper {
    public static ProfileDto mapToProfileDto(Profile profile, ProfileDto profileDto) {
        profileDto.setName(profile.getName());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        return profileDto;
    }
}
