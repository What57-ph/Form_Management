package com.example.formmanagement.domain.response;

import com.example.formmanagement.utils.enums.FieldType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseFieldDTO {
    Long fieldId;
    String label;
    FieldType type;
    Integer order;
    Boolean required;
    List<String> options;
    Instant createdAt;
    Instant updatedAt;
}
