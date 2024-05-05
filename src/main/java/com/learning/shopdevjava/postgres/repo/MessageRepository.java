package com.learning.shopdevjava.postgres.repo;

import com.learning.shopdevjava.postgres.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{
    MessageEntity findByText(String text);
}
