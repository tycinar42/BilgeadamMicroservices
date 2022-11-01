package com.tyc.utility;

import com.tyc.dto.resquest.UserProfileRequestDto;
import com.tyc.manager.IUserProfileManager;
import com.tyc.repository.entity.UserProfile;
import com.tyc.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserComponent {

    private final IUserProfileManager userProfileManager;
    private final UserProfileService userProfileService;

//    @PostConstruct
//    public void firstRun() {
//        List<UserProfile> userProfiles = userProfileManager.userList().getBody();
//        userProfiles.forEach(userProfile -> {
//            userProfile.setId(null);
//            userProfile.setUserId(Long.getLong(userProfile.getId()));
//            userProfileService.save(userProfile);
//        });
//
////        userProfileService.saveAll(userProfiles);
//    }
}
