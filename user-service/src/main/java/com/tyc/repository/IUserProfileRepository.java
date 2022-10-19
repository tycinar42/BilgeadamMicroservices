package com.tyc.repository;

import com.tyc.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);
}
