package com.tyc.service;

import com.tyc.dto.request.GetAllOnlineRequestDto;
import com.tyc.dto.request.GetMyProfileRequestDto;
import com.tyc.repository.IOnlineRepository;
import com.tyc.repository.entity.Online;
import com.tyc.repository.entity.UserProfile;
import com.tyc.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineService extends ServiceManager<Online, String> {
    private final IOnlineRepository repository;
    private final UserProfileService userProfileService;

    public OnlineService(IOnlineRepository repository, UserProfileService userProfileService) {
        super(repository);
        this.repository = repository;
        this.userProfileService = userProfileService;
    }

    public List<Online> getAllOnlineList() {
        return repository.findByOnlineTrue();
    }

    /**
     * Token ile birlikte userProfileService'ten ilgili kisiye ait profil bigisi cekilir.
     * Sonra bu bilgiler ile online olma islemleri yapilir.
     * @param token
     */
    public void doOnline(String token) {
        UserProfile userProfile = userProfileService.findByToken(
                GetMyProfileRequestDto.builder().token(token).build()
        );
        /**
         * Kisinin daha onceden online/offline bilgisinin kayitli olup olmadigina bakiyoruz.
         */
        Optional<Online> online = repository.findOptionalByUserprofileid(userProfile.getId());
        if(online.isEmpty()) {
            repository.save(Online.builder()
                            .online(true)
                            .userprofileid(userProfile.getId())
                    .build());
        } else {
            online.get().setOnline(true);
            save(online.get());
        }
    }

    public void doOffline(String token) {
        UserProfile userProfile = userProfileService.findByToken(
                GetMyProfileRequestDto.builder().token(token).build()
        );
        /**
         * Kisinin daha onceden online/offline bilgisinin kayitli olup olmadigina bakiyoruz.
         */
        Optional<Online> online = repository.findOptionalByUserprofileid(userProfile.getId());
        if(online.isEmpty()) {
            repository.save(Online.builder()
                    .online(false)
                    .userprofileid(userProfile.getId())
                    .build());
        } else {
            online.get().setOnline(false);
            save(online.get());
        }
    }
}
