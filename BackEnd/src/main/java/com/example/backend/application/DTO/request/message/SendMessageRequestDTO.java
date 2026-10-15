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
public class SendMessageRequestDTO {

    private UUID conversationId;

    private String recipientId;

    private String recipientName;

    @NotBlank
    private String content;

    @Pattern(regexp = "(?i)normal|urgent", message = "priority deve ser normal ou urgent")
    private String priority;
}