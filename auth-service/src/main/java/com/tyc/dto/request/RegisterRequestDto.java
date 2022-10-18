package com.tyc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotNull(message = "Username must not null")
    @Size(min = 3, max = 16)
    private String username;
    @NotNull(message = "Password must not null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
            message = "The password must including minimum 8 characters, a big letter, a small letter, a number and a special character.")
    @Size(min = 8, max = 64, message = "The password must be between 8-64 characters")
    private String password;
    @Email(message = "Please writing valid email")
    @NotNull(message = "Email must not null")
    private String email;
    private String admincode;
}
