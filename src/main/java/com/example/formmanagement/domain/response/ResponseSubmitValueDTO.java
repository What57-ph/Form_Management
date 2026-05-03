package com.example.formmanagement.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseSubmitValueDTO {
    Long submitValueId;
    String value;
    Long fieldId;
    String fieldLabel;
}
