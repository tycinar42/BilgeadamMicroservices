package com.tyc.config.security;

import com.tyc.dto.request.FindByAuthIdRequestDto;
import com.tyc.dto.response.FindByAuthIdResponseDto;
import com.tyc.manager.IUserServiceManager;
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
    IUserServiceManager userServiceManager;
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
                FindByAuthIdResponseDto userProfile = userServiceManager.findByAuthId(FindByAuthIdRequestDto.builder()
                                .authId(authId.get())
                        .build()).getBody();
                if(userProfile.getUserId() != null) {
                    UserDetails userDetails = jwtMyUser.loadByAuthId(userProfile, authId.get());
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
