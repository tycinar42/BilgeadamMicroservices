package com.tyc.graphql.model;

import lombok.Data;

@Data
public class UserProfileInput {
    private Long authId;
    private String username;
    private String email;
}
