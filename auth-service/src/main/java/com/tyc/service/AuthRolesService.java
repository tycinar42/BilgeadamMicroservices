package com.tyc.service;

import com.tyc.repository.IAuthRolesRepository;
import com.tyc.repository.entity.AuthRoles;
import com.tyc.repository.entity.Authorities;
import com.tyc.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthRolesService extends ServiceManager<AuthRoles, Long> {
    private final IAuthRolesRepository repository;
    private final AuthoritiesService authoritiesService;

    public AuthRolesService(IAuthRolesRepository repository, AuthoritiesService authoritiesService) {
        super(repository);
        this.repository = repository;
        this.authoritiesService = authoritiesService;
    }
    public List<String> getRolesByAuthId(Long authId) {
        /**
         * Oncelikle authId'ye ait tum role listesini cekiyoruz.
         */
        List<AuthRoles> authRoles = repository.findAllByAuthId(authId);
        /**
         * Cekilen listedeki roles id'leri ile Authorities tablosundan ilgili rolleri cekiyoruz.
         */
        List<String> roles = new ArrayList<>();
        authRoles.forEach(r -> {
            Authorities authorities = authoritiesService.findById(r.getRoleId());
            if(authorities != null) {
                roles.add(authorities.getName());
            }
        });
        return roles;
    }
}
