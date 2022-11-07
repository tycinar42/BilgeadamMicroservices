package com.tyc.service;

import com.tyc.repository.IAuthoritiesRepository;
import com.tyc.repository.entity.Authorities;
import com.tyc.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService extends ServiceManager<Authorities, Long> {
    private final IAuthoritiesRepository repository;

    public AuthoritiesService(IAuthoritiesRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
