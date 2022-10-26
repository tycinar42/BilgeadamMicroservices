package com.tyc.repository;

import com.tyc.repository.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);
}
