package com.tyc.service;

import com.tyc.dto.request.UserProfileSaveRequestDto;
import com.tyc.dto.request.UserProfileUpdateRequestDto;
import com.tyc.exception.ErrorType;
import com.tyc.exception.UserServiceException;
import com.tyc.repository.IUserProfileRepository;
import com.tyc.repository.entity.UserProfile;
import com.tyc.utility.ServiceManager;
import com.tyc.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;
    private final TokenManager tokenManager;

    public UserProfileService(IUserProfileRepository repository, TokenManager tokenManager) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
    }

    public Boolean save(UserProfileSaveRequestDto dto) {
        save(UserProfile.builder()
                .authId(dto.getAuthId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto) {
        Long authId = tokenManager.getId(dto.getToken());
        if(authId == null) throw new UserServiceException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findById(authId);
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.USER_DID_NOT_FIND);
        UserProfile profile = userProfile.get();
        profile.setAddress(dto.getAddress());
        profile.setPhone(dto.getPhone());
        profile.setAvatar(dto.getAvatar());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        save(profile);
        return true;
    }

}
