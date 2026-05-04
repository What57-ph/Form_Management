package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Size(min = 2, max = 255, message = "Label must not exceed 255 characters")
    String label;
    FieldType type;
    @Min(value = 0, message = "Order must be >= 0")
    Integer order;
    Boolean required;
    List<@Pattern(regexp = "^[^;]*$", message = "Option must not contain ';'") String> options;
}
