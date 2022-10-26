package com.tyc.service;

import com.tyc.dto.request.UserProfileSaveRequestDto;
import com.tyc.dto.request.UserProfileUpdateRequestDto;
import com.tyc.exception.ErrorType;
import com.tyc.exception.UserServiceException;
import com.tyc.manager.IElasticSearchManager;
import com.tyc.repository.IUserProfileRepository;
import com.tyc.repository.entity.UserProfile;
import com.tyc.utility.JwtTokenManager;
import com.tyc.utility.ServiceManager;
import com.tyc.utility.TokenManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;
//    private final TokenManager tokenManager;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;
    private final IElasticSearchManager elasticSearchManager;

    public UserProfileService(IUserProfileRepository repository, JwtTokenManager tokenManager, CacheManager cacheManager,
                              IElasticSearchManager elasticSearchManager) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.cacheManager = cacheManager;
        this.elasticSearchManager = elasticSearchManager;
    }

    @Cacheable(value = "uppercase")
    public String getUpperCase(Long authId) {
        /**
         * Bu kisim method'un belli islem basamaklarini simule etmek ve belli zaman alacak islemleri
         * gostermek icin yazilmistir.
         */
        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        Optional<UserProfile> user = repository.findOptionalByAuthId(authId);
        if(user.isEmpty()) return "";
        return user.get().getName().toUpperCase();
    }

    public Boolean save(UserProfileSaveRequestDto dto) {
        UserProfile userProfile = save(UserProfile.builder()
                .authId(dto.getAuthId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build());
        elasticSearchManager.save(userProfile);
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto) {
        Optional<Long> authId = tokenManager.getByIdFromToken(dto.getToken());
        if(authId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_ID);
        Optional<UserProfile> userProfile = repository.findById(authId.get());
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.USER_DID_NOT_FIND);
        UserProfile profile = userProfile.get();
        profile.setAddress(dto.getAddress());
        profile.setPhone(dto.getPhone());
        profile.setAvatar(dto.getAvatar());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        save(profile);
        elasticSearchManager.update(profile);
        return true;
    }

    public void updateCacheReset(UserProfile profile) {
        save(profile);
        /**
         * Bu islem ilgili method tarafindan tutulan tum onbelleklenmis datayi temizler
         * cok istemedigimiz gerekli oldugunda kullanmamiz gereken bir yapidir
         * cacheManager.getCache("uppercase")
         */
        cacheManager.getCache("uppercase").evict(profile.getAuthId());
    }

}
