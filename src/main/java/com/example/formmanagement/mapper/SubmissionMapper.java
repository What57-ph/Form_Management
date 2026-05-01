package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Submission;
import com.example.formmanagement.domain.response.ResponseSubmitDTO;
import com.example.formmanagement.domain.response.ResponseSubmitValueDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubmissionMapper {
    ModelMapper modelMapper;
    FormMapper formMapper;

    public ResponseSubmitDTO toResponseSubmitDTO(Submission submission){

        ResponseSubmitDTO dto = modelMapper.map(submission, ResponseSubmitDTO.class);
        dto.setSubmissionId(submission.getId());
        if (dto.getForm() == null) {
            dto.setForm(ResponseSubmitDTO.SubmitForm.builder()
                    .formId(submission.getForm().getId())
                    .formTitle(submission.getForm().getTitle())
                    .build());
        }

        if (submission.getSubmissionValues() != null){
            dto.getForm().setSubmitValues(
                    submission.getSubmissionValues().stream().map(value ->
                            ResponseSubmitValueDTO.builder()
                                    .submitValueId(value.getId())
                                    .value(value.getValue())
                                    .fieldLabel(value.getField().getLabel())
                                    .build()
                    ).collect(Collectors.toList())
            );
        }

        return dto;
    }
}