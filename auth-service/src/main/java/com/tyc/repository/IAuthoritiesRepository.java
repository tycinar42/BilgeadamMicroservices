package com.tyc.repository;

import com.tyc.repository.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthoritiesRepository extends JpaRepository<Authorities, Long> {
    Optional<Authorities> findOptionalByName(String name);
}
