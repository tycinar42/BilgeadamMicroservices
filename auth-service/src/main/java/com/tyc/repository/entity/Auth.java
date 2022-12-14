package com.tyc.repository.entity;

import com.tyc.repository.enums.Activated;
import com.tyc.repository.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tblauth")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    Activated activated;
    @Enumerated(EnumType.STRING)
    Roles roles;
}
