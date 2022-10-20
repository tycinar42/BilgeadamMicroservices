package com.tyc.service;

import com.tyc.dto.request.LoginRequestDto;
import com.tyc.dto.request.RegisterRequestDto;
import com.tyc.dto.request.UserProfileSaveRequestDto;
import com.tyc.exception.AuthServiceException;
import com.tyc.exception.ErrorType;
import com.tyc.manager.IUserProfileManager;
import com.tyc.repository.IAuthRepository;
import com.tyc.repository.entity.Auth;
import com.tyc.repository.enums.Roles;
import com.tyc.utility.JwtTokenManager;
import com.tyc.utility.ServiceManager;
import com.tyc.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository repository;
    private final IUserProfileManager userProfileManager;
//    private final TokenManager tokenManager;
    private final JwtTokenManager tokenManager;

    public AuthService(IAuthRepository repository, IUserProfileManager userProfileManager, JwtTokenManager tokenManager) {
        super(repository);
        this.repository = repository;
        this.userProfileManager = userProfileManager;
        this.tokenManager = tokenManager;
    }

    public Boolean save(RegisterRequestDto dto) {
        Auth auth = Auth.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(Roles.ROLE_USER)
                .build();
        if(dto.getAdmincode() != null && dto.getAdmincode().equals("Adm!n"))
            auth.setRoles(Roles.ROLE_ADMIN);

        save(auth);
        if (auth.getId() != null) {
            userProfileManager.save(UserProfileSaveRequestDto.builder()
                            .authId(auth.getId())
                            .email(auth.getEmail())
                            .username(auth.getUsername())
                    .build());
            return true;
        }
        return false;
    }

    public String  doLogin(LoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(auth.isEmpty()) throw new AuthServiceException(ErrorType.LOGIN_ERROR_001);
        return tokenManager.createToken(auth.get().getId());
    }
}
