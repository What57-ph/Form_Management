package com.example.formmanagement.domain.request;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Submission;
import com.example.formmanagement.utils.enums.FormStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseForm {
    Long id;
    String title;
    String description;
    List<String> order;
    FormStatus status;
    Instant createdAt;
    Instant updatedAt;
}
