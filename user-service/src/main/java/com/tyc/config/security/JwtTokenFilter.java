package com.tyc.config.security;

import com.tyc.repository.entity.UserProfile;
import com.tyc.service.UserProfileService;
import com.tyc.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    UserProfileService service;
    @Autowired
    JwtMyUser jwtMyUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("Burasi her zaman calisir");
        /**
         * Biz burada araya girecegiz ve yetki islemlerimizi yapacagiz
         */
        final String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<Long> authId = jwtTokenManager.getByIdFromToken(token);
            if(authId.isPresent()) {
                Optional<UserProfile> userProfile = service.findOptionalByAuthId(authId.get());
                if(userProfile.isPresent()) {
                    UserDetails userDetails = jwtMyUser.loadByAuthId(userProfile.get().getAuthId());
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
