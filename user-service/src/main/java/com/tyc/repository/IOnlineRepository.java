package com.tyc.repository;

import com.tyc.repository.entity.Online;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOnlineRepository extends MongoRepository<Online, String> {
    Optional<Online> findOptionalByUserprofileid(String id);
    Optional<List> findAllByOnline(Boolean online);
    List<Online> findByOnlineTrue();
    List<Online> findByOnlineFalse();
}
