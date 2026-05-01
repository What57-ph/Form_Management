package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.model.Submission;
import com.example.formmanagement.domain.model.SubmissionValue;
import com.example.formmanagement.domain.request.RequestSubmitDTO;
import com.example.formmanagement.domain.response.ResponseSubmitDTO;
import com.example.formmanagement.mapper.SubmissionMapper;
import com.example.formmanagement.repository.FieldRepository;
import com.example.formmanagement.repository.FormRepository;
import com.example.formmanagement.repository.SubmissionRepository;
import com.example.formmanagement.repository.SubmissionValueRepository;
import com.example.formmanagement.utils.exception.FieldValidationException;
import com.example.formmanagement.utils.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubmissionService {
    SubmissionRepository submissionRepository;
    SubmissionValueRepository submissionValueRepository;
    FormRepository formRepository;
    FieldService fieldService;
    SubmissionMapper submissionMapper;

    @Transactional
    public ResponseSubmitDTO handleSubmitForm(Long formId, RequestSubmitDTO requestSubmitDTO) throws FieldValidationException {

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new NotFoundException("Cannot find form"));

        List<Long> validFieldIds = form.getFields().stream()
                .map(Field::getId)
                .collect(Collectors.toList());

        for (RequestSubmitDTO.FieldValue reqField : requestSubmitDTO.getFieldValueList()) {

            if (!validFieldIds.contains(reqField.getFieldId())) {
                throw new FieldValidationException("Field ID " + reqField.getFieldId() + " does not belong to this form.");
            }

            fieldService.validateField(reqField.getFieldType(), reqField.getValue(), reqField.getOptions());
        }

        Submission submission = submissionRepository.save(Submission.builder().form(form).build());
        List<SubmissionValue> submissionValues = new LinkedList<>();
        for (RequestSubmitDTO.FieldValue reqField : requestSubmitDTO.getFieldValueList()) {
            Field fieldEntity = fieldService.findFieldById(reqField.getFieldId());

            if (!fieldEntity.getType().equals(reqField.getFieldType())) {
                throw new FieldValidationException("Type mismatch for field: " + fieldEntity.getLabel());
            }


                submissionValues.add(submissionValueRepository.save(SubmissionValue.builder()
                        .submission(submission)
                        .value(reqField.getValue())
                        .field(fieldEntity)
                        .build())
                );

        }
        submission.setSubmissionValues(submissionValues);
        return submissionMapper.toResponseSubmitDTO(submission);
    }

    public List<ResponseSubmitDTO> getSubmittedForm(){
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream().map(submissionMapper::toResponseSubmitDTO)
                .collect(Collectors.toList());
    }
}
