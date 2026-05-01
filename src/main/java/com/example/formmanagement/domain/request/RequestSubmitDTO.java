package com.example.formmanagement.domain.request;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestSubmitDTO {
    List<FieldValue> fieldValueList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FieldValue {
        Long fieldId;
        FieldType fieldType;
        String value;
        @Nullable
        List<String> options;
    }
}
