package com.example.backend.application.DTO.response.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueStatusResponseDTO {

    private int normalQueueSize;
    private int urgentQueueSize;

    private long queued;
    private long processing;
    private long sent;
    private long delivered;
    private long failed;
}
