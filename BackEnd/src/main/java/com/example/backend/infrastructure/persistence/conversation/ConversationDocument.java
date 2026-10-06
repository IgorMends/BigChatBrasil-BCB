package com.example.backend.infrastructure.persistence.conversation;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("conversation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDocument {
}
