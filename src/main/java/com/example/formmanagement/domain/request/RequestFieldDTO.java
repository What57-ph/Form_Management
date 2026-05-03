package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestFieldDTO {
    @Nullable
    @Size(min = 2, max = 255, message = "Label must not exceed 255 characters")
    String label;
    @Nullable
    FieldType type;
    @Nullable
    Integer order;
    @Nullable
    Boolean required;
    @Nullable
    List<String> options;
}
