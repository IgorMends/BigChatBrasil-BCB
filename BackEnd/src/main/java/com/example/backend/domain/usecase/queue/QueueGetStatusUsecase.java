package com.example.backend.domain.usecase.queue;

import com.example.backend.application.DTO.response.queue.QueueStatusResponseDTO;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.domain.port.MessageQueue;
import com.example.backend.domain.port.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueGetStatusUsecase {

    private final MessageQueue messageQueue;
    private final MessageRepository messageRepository;

    public QueueStatusResponseDTO execute() {
        return QueueStatusResponseDTO.builder()
                .normalQueueSize(messageQueue.normalSize())
                .urgentQueueSize(messageQueue.urgentSize())
                .queued(messageRepository.countByStatus(MessageStatus.QUEUED))
                .processing(messageRepository.countByStatus(MessageStatus.PROCESSING))
                .sent(messageRepository.countByStatus(MessageStatus.SENT))
                .delivered(messageRepository.countByStatus(MessageStatus.DELIVERED))
                .failed(messageRepository.countByStatus(MessageStatus.FAILED))
                .build();
    }
}