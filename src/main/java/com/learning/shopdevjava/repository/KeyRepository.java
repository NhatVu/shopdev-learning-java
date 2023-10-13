package com.learning.shopdevjava.repository;

import com.learning.shopdevjava.entity.KeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends MongoRepository<KeyEntity, String> {
    KeyEntity findByUserId(String userId);

    @Query("{'userId' : :#{#userId}, 'refreshTokensUsed': :#{#refreshToken}}")
    KeyEntity findByUserIdAndRefreshTokenUsed(@Param("userId") String userId, @Param("refreshToken") String refreshToken);
}
