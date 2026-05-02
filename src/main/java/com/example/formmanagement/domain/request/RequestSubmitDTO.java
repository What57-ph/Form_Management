package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestSubmitDTO {

    @NotNull(message = "Field value list must not be null")
    @Size(min = 1, message = "Field value list must not be empty")
    @Valid
    List<FieldValue> fieldValueList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FieldValue {
        @NotNull(message = "Field ID must not be null")
        Long fieldId;

        @NotNull(message = "Field type must not be null")
        FieldType fieldType;

        @NotBlank(message = "Value must not be blank")
        String value;
    }
}
