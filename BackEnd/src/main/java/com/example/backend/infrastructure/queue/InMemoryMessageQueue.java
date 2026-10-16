package com.example.backend.infrastructure.queue;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.port.MessageQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMessageQueue implements MessageQueue {

    private static final int MAX_CONSECUTIVE_URGENT = 3;

    private final Deque<MessageEntity> urgent = new ArrayDeque<>();
    private final Deque<MessageEntity> normal = new ArrayDeque<>();
    private int consecutiveUrgent = 0;

    @Override
    public synchronized void enqueue(MessageEntity message) {
        if (message.getPriority() == MessagePriority.URGENT) {
            urgent.addLast(message);
        } else {
            normal.addLast(message);
        }
    }

    @Override
    public synchronized Optional<MessageEntity> dequeue() {
        boolean urgentTurn = !urgent.isEmpty()
                && (consecutiveUrgent < MAX_CONSECUTIVE_URGENT || normal.isEmpty());

        if (urgentTurn) {
            consecutiveUrgent++;
            return Optional.of(urgent.pollFirst());
        }

        if (!normal.isEmpty()) {
            consecutiveUrgent = 0;
            return Optional.of(normal.pollFirst());
        }

        return Optional.empty();
    }

    @Override
    public synchronized int urgentSize() {
        return urgent.size();
    }

    @Override
    public synchronized int normalSize() {
        return normal.size();
    }
}
