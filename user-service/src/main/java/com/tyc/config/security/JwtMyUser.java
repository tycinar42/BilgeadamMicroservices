package com.tyc.config.security;

import com.tyc.dto.request.GetAllRolesRequestDto;
import com.tyc.manager.IAuthServiceManager;
import com.tyc.repository.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtMyUser implements UserDetailsService {
    private final IAuthServiceManager authServiceManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadByAuthId(UserProfile userProfile) {
        List<String> roles = authServiceManager.getAllRolesByAuthId(
                GetAllRolesRequestDto.builder()
                        .authId(userProfile.getAuthId())
                        .build()
        ).getBody();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
        return User.builder()
                .username(userProfile.getUsername())
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .authorities(grantedAuthorities)
                .build();
    }
}
