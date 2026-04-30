package com.example.formmanagement.domain.response;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseField {
    Long id;
    String label;
    FieldType type;
    List<String> order;
    Boolean required;
    List<String> options;
    Instant createdAt;
    Instant updatedAt;
}
