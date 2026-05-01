package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
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
    @Size(min = 2, max = 255, message = "Label should be between 2 and 255 characters")
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
