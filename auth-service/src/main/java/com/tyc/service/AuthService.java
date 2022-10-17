package com.tyc.service;

import com.tyc.repository.IAuthRepository;
import com.tyc.repository.entity.Auth;
import com.tyc.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository repository;
    public AuthService(IAuthRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
