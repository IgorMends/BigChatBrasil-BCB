package com.example.backend.domain.usecase.message;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.domain.port.MessageQueue;
import com.example.backend.domain.port.MessageRepository;
import com.example.backend.domain.usecase.transaction.TransactionRefundClientUsecase;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessNextUsecase {

    private final MessageQueue messageQueue;
    private final MessageRepository messageRepository;
    private final TransactionRefundClientUsecase refundUsecase;

    @Value("${app.queue.failure-rate:0.0}")
    private double failureRate;

    public boolean execute() {
        Optional<MessageEntity> next = messageQueue.dequeue();
        if (next.isEmpty()) {
            return false;
        }

        MessageEntity message = next.get();
        messageRepository.updateStatus(message.getId(), MessageStatus.PROCESSING);

        if (simulateSend(message)) {
            messageRepository.updateStatus(message.getId(), MessageStatus.SENT);
            log.info("Mensagem {} enviada ({})", message.getId(), message.getPriority());
        } else {
            refundUsecase.execute(UUID.fromString(message.getSenderId()), message.getCost(), message.getId());
            messageRepository.updateStatus(message.getId(), MessageStatus.FAILED);
            log.warn("Mensagem {} falhou e o valor foi estornado", message.getId());
        }

        return true;
    }

    private boolean simulateSend(MessageEntity message) {
        return ThreadLocalRandom.current().nextDouble() >= failureRate;
    }
}
