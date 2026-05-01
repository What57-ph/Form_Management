package com.example.formmanagement.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseSubmitDTO {
    Long submissionId;
    SubmitForm form;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubmitForm {
        Long formId;
        String formTitle;
        List<ResponseSubmitValueDTO> submitValues;
    }
}
