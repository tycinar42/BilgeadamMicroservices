package com.tyc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tblauthroles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authId;
    private Long roleId;
    private Long addDate;
    private Long addUser;
    private Boolean state;
}
