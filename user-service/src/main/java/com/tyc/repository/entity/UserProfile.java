package com.tyc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tbluserprofile")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
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
