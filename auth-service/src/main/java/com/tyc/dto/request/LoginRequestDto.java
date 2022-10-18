package com.tyc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequestDto {
    @NotNull(message = "Username must not null")
    @Size(min = 3, max = 16)
    private String username;
    @NotNull(message = "Password must not null")
    @Size(min = 8, max = 64, message = "The password must be between 8-64 characters")
    private String password;
}
