package com.example.backend.domain.port;

import com.example.backend.domain.entity.MessageEntity;
import java.util.Optional;

public interface MessageQueue {

    void enqueue(MessageEntity message);

    Optional<MessageEntity> dequeue();

    int urgentSize();

    int normalSize();
}