package com.tyc.service;

import com.tyc.dto.resquest.UserProfileRequestDto;
import com.tyc.graphql.model.UserProfileInput;
import com.tyc.repository.IUserProfileRepository;
import com.tyc.repository.entity.UserProfile;
import com.tyc.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;

    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void save(UserProfileRequestDto dto) {
        save(UserProfile.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .avatar(dto.getAvatar())
                .authId(dto.getAuthId())
                .userId(dto.getId())
                .email(dto.getEmail())
                .build());
    }

    public Boolean saveInput(UserProfileInput userProfileInput) {
        save(UserProfile.builder()
                .authId(userProfileInput.getAuthId())
                .username(userProfileInput.getUsername())
                .email(userProfileInput.getEmail())
                .build());
        /**
         * Eger bu sekilde kayit isleyeceksek, o zaman gercek datalarin
         * tutuldugu islemlerinin yapildigi microservice'lere ilgili kaydi
         * gecmek zorundayiz. Bu kisimda manager kullanarak kayi islemini
         * baslatabiliriz.
         */
        return true;
    }
}
