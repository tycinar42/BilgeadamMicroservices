package com.tyc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileUpdateRequestDto {
    private String token;
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String avatar;
}
