package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FormStatus;
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
public class RequestFormDTO {

    @Nullable
    String title;
    @Nullable
    String description;
    @Nullable
    List<String> order;
    @Nullable
    FormStatus status;
    Instant createdAt;
    Instant updatedAt;
}
