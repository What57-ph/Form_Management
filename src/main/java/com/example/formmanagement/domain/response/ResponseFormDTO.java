package com.example.formmanagement.domain.response;

import com.example.formmanagement.utils.enums.FormStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseFormDTO {
    Long formId;
    String title;
    String description;
    Integer order;
    FormStatus status;
    Instant createdAt;
    Instant updatedAt;
    List<ResponseFieldDTO> fields;
//    List<ResponseFieldDTO> responses;

}
