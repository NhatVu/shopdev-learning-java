package com.learning.shopdevjava.postgres.repo;

import com.learning.shopdevjava.postgres.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Slf4j
@AutoConfigureDataMongo
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private MessageEntity message;

    @BeforeEach
    void setUp() {
        // Create a sample message entity
        message = MessageEntity.builder()
                .text("Hello, World!")
                .build();
    }

    @Test
    void shouldSaveMessage() {
        // Save the message
        MessageEntity savedMessage = messageRepository.save(message);
        log.info("savedMessage: {}", savedMessage);
        // Verify the saved message
        assertNotNull(savedMessage.getId());
        assertEquals(message.getText(), savedMessage.getText());
    }

}
