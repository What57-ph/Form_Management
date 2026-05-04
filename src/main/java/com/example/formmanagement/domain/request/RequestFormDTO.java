package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FormStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    String title;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description;
    @Min(value = 0, message = "Order must be >= 0")
    Integer order;
    FormStatus status;
}
