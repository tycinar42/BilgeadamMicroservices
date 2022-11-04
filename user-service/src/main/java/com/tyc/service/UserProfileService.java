package com.tyc.service;

import com.tyc.dto.request.GetMyProfileRequestDto;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
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

    public Optional<UserProfile> findOptionalByAuthId(Long authId) {
        return repository.findOptionalByAuthId(authId);
    }
    public UserProfile findByToken(GetMyProfileRequestDto dto) {
        Optional<Long> authId = tokenManager.getByIdFromToken(dto.getToken());
        if(authId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_ID);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.USER_DID_NOT_FIND);
        return userProfile.get();
        /*
        GlobalException burada olusacak hatayi yakalayabilir; ama istersek biz de bu hatayi kontrol edebiliriz.
        try {
            Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        } catch (Exception e) {
            throw new UserServiceException(ErrorType.INVALID_ID);
        }
         */
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
//        elasticSearchManager.save(userProfile);
        return true;
    }

    public Boolean updateUnToken(UserProfileUpdateRequestDto dto) {
//        Optional<Long> authId = tokenManager.getByIdFromToken(dto.getToken());
//        if(authId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_ID);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(dto.getAuthId());
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.USER_DID_NOT_FIND);
        UserProfile profile = userProfile.get();
        profile.setAddress(dto.getAddress());
        profile.setPhone(dto.getPhone());
        profile.setAvatar(dto.getAvatar());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        save(profile);
//        elasticSearchManager.update(profile);
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto) {
        Optional<Long> authId = tokenManager.getByIdFromToken(dto.getToken());
        if(authId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_ID);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.USER_DID_NOT_FIND);
        UserProfile profile = userProfile.get();
        profile.setAddress(dto.getAddress());
        profile.setPhone(dto.getPhone());
        profile.setAvatar(dto.getAvatar());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        save(profile);
//        elasticSearchManager.update(profile);
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

    /**
     *
     * ORN: 500 adet kayit var
     *  DIKKAT: Sayfa sayilari 0(Sifir)'dan baslar
     *  - 10'ar aet kayit gostermek istedigimizde 50 adet sayfa olusur.
     *  - 2. sayfayi istedigimizde 21-30. kayitlar gosterilir
     * @param pageSize -> her seferinde kac kayit donecegini doner
     * @param currentPageNumber -> gecerli sayfanin hangisi oldugunu belirler
     * @param sortParameter -> siralama isleminin hangi kolona gore yapilacagini belirler
     * @param sortDirection -> siralama yonu, ASC, DESC
     * @return
     */
    public Page<UserProfile> getAllPage(int pageSize, int currentPageNumber, String sortParameter, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        Pageable pageable = PageRequest.of(currentPageNumber, pageSize, sort);
        return repository.findAll(pageable);
    }
    public Slice<UserProfile> getAllSlice(int pageSize, int currentPageNumber, String sortParameter, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        Pageable pageable = PageRequest.of(currentPageNumber, pageSize, sort);
        return repository.findAll(pageable);
    }

}
