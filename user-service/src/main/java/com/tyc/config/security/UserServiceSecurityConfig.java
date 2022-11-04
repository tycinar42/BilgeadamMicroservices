package com.tyc.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserServiceSecurityConfig {

    @Bean
    public JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.csrf().disable();
        /**
         * Tum gelen isteklere izin verme
         * http://localhost:9092/v3/api-docs/**[her sey]
         */
        http.authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // izin ver
                .anyRequest().authenticated(); // izin verme
//                .antMatchers("v3/api-docs/**", "/swagger-ui/**", "/api/v1/user/userlist").authenticated()
//                .anyRequest().permitAll();
        /**
         * Kullanici dogrulama islemini spring form ile yap
         */
//        http.formLogin();
        /**
         * Gelen her istegi kendi olusturdugumuz bir sinif icerisine yonlendirecegiz ve burada
         * gelen token bilgisini kontrol ederek icinde var olan authid ile kullanici bilgilerini
         * cekecegiz. Bu bilgilerin yetki vs. gibi alanlari ile islem yapmasini saglayacagiz.
         */
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
