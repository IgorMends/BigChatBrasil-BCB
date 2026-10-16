package com.example.backend.infrastructure.queue;

import com.example.backend.domain.usecase.message.MessageProcessNextUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageQueueWorker {

    private static final int MAX_PER_CYCLE = 10;

    private final MessageProcessNextUsecase processNextUsecase;

    @Scheduled(fixedDelayString = "${app.queue.poll-interval-ms:1000}")
    public void run() {
        try {
            int processed = 0;
            while (processed < MAX_PER_CYCLE && processNextUsecase.execute()) {
                processed++;
            }
        } catch (Exception e) {
            log.error("Erro ao processar a fila", e);
        }
    }
}

