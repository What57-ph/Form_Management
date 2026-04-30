package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestField {
    @Nullable
    Long id;
    @Nullable
    String label;
    @Nullable
    FieldType type;
    @Nullable
    List<String> order;
    @Nullable
    Boolean required;
    @Nullable
    List<String> options;
}
