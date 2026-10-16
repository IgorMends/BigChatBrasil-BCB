package com.example.backend.domain.usecase.message;

import com.example.backend.application.DTO.apiMappers.MessageApiMapper;
import com.example.backend.application.DTO.request.message.SendMessageRequestDTO;
import com.example.backend.application.DTO.response.message.SendMessageResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.domain.exception.ResourceNotFoundException;
import com.example.backend.domain.port.ConversationRepository;
import com.example.backend.domain.port.MessageQueue;
import com.example.backend.domain.port.MessageRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.backend.domain.usecase.transaction.TransactionChargeClientUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class MessageSendUsecase {

    private static final BigDecimal NORMAL_COST = new BigDecimal("0.25");
    private static final BigDecimal URGENT_COST = new BigDecimal("0.50");

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final TransactionChargeClientUsecase chargeClientUsecase;
    private final MessageQueue messageQueue;

    @Transactional
    public SendMessageResponseDTO execute(UUID clientId, SendMessageRequestDTO request) {
        MessagePriority priority = parsePriority(request.getPriority());
        BigDecimal cost = costOf(priority);
        LocalDateTime now = LocalDateTime.now();

        ConversationEntity conversation = findOrCreateConversation(clientId, request);

        MessageEntity message = messageRepository.save(MessageEntity.builder()
                .conversationId(conversation.getId())
                .senderId(clientId.toString())
                .senderType("client")
                .recipientId(conversation.getRecipientId())
                .content(request.getContent())
                .priority(priority)
                .status(MessageStatus.QUEUED)
                .cost(cost)
                .createdAt(now)
                .build());

        BigDecimal currentBalance = chargeClientUsecase.execute(clientId, cost, message.getId());

        conversation.setLastMessageContent(message.getContent());
        conversation.setLastMessageTime(now);
        conversationRepository.save(conversation);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messageQueue.enqueue(message);
            }
        });

        return new SendMessageResponseDTO(
                message.getId(),
                MessageApiMapper.lower(message.getStatus()),
                now.plusSeconds(5),
                cost,
                currentBalance);
    }

    private ConversationEntity findOrCreateConversation(UUID clientId, SendMessageRequestDTO request) {
        if (request.getConversationId() != null) {
            return conversationRepository.findByIdAndClientId(request.getConversationId(), clientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));
        }

        if (request.getRecipientId() == null || request.getRecipientId().isBlank()) {
            throw new IllegalArgumentException("Informe conversationId ou recipientId");
        }

        return conversationRepository.findByClientIdAndRecipientId(clientId, request.getRecipientId())
                .orElseGet(() -> conversationRepository.save(ConversationEntity.builder()
                        .clientId(clientId)
                        .recipientId(request.getRecipientId())
                        .recipientName(request.getRecipientName())
                        .unreadCount(0)
                        .build()));
    }

    private MessagePriority parsePriority(String priority) {
        return priority == null ? MessagePriority.NORMAL : MessagePriority.valueOf(priority.toUpperCase());
    }

    private BigDecimal costOf(MessagePriority priority) {
        return priority == MessagePriority.URGENT ? URGENT_COST : NORMAL_COST;
    }
}