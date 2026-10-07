package com.example.backend.application.DTO.request.conversation;

import jakarta.validation.constraints.NotBlank;
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
public class ConversationCreateRequestDTO {

    @NotBlank
    private String recipientId;
    @NotBlank
    private String recipientName;
    @NotBlank
    private String recipientPhoneNumber;
}
