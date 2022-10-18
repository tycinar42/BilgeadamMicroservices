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
    Long authId;
    String username;
    String name;
    String surname;
    String email;
    String phone;
    String address;
    String avatar;

}
