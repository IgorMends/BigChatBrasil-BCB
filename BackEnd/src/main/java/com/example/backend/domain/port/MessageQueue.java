package com.example.backend.domain.port;

import com.example.backend.domain.entity.MessageEntity;

public interface MessageQueue {

    void enqueue(MessageEntity message);
}