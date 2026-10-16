package com.example.backend.infrastructure.queue;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.domain.port.MessageQueue;
import com.example.backend.domain.port.MessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageQueueRecovery {

    private final MessageRepository messageRepository;
    private final MessageQueue messageQueue;

    @EventListener(ApplicationReadyEvent.class)
    public void recover() {
        List<MessageEntity> pending = messageRepository.findByStatus(MessageStatus.QUEUED);
        pending.forEach(messageQueue::enqueue);
        log.info("{} mensagem(ns) queued recarregada(s) na fila", pending.size());
    }
}
