package com.tyc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindByAuthIdResponseDto {
    private String userId;
    private String username;
    private String name;
    private String avatar;
}
