package com.example.backend.domain.port;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.port.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoOpMessageQueue implements MessageQueue {

    @Override
    public void enqueue(MessageEntity message) {
        log.warn("MessageQueue ainda não implementada. Mensagem não foi enfileirada: {}", message);
    }
}