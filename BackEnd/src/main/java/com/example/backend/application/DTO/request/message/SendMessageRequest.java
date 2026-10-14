package com.example.backend.application.DTO.request.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    // Opcional: se vier nulo, a conversa é localizada/criada pelo recipientId
    private UUID conversationId;

    private String recipientId;

    // Usado apenas quando a conversa for criada neste envio
    private String recipientName;

    @NotBlank
    private String content;

    // normal | urgent (nulo = normal)
    @Pattern(regexp = "(?i)normal|urgent", message = "priority deve ser normal ou urgent")
    private String priority;
}