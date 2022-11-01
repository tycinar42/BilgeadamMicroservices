package com.tyc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class UserProfile {

    @Id
    private String id;
    /**
     * Auth servisinden kayit olan kisinin auth id'sini buraya esitliyoruz.
     */
    private Long authId;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String avatar;

}
