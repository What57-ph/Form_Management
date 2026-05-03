package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.model.Submission;
import com.example.formmanagement.domain.model.SubmissionValue;
import com.example.formmanagement.domain.request.RequestSubmitDTO;
import com.example.formmanagement.domain.response.ResponsePaginationDTO;
import com.example.formmanagement.domain.response.ResponseSubmitDTO;
import com.example.formmanagement.mapper.SubmissionMapper;
import com.example.formmanagement.repository.FormRepository;
import com.example.formmanagement.repository.SubmissionRepository;
import com.example.formmanagement.repository.SubmissionValueRepository;
import com.example.formmanagement.utils.exception.FieldValidationException;
import com.example.formmanagement.utils.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


        Submission submission = Submission.builder().form(form).build();
        List<SubmissionValue> submissionValues = new LinkedList<>();
        for (RequestSubmitDTO.FieldValue reqField : requestSubmitDTO.getFieldValueList()) {
            if (!validFieldIds.contains(reqField.getFieldId())) {
                throw new FieldValidationException("Field ID " + reqField.getFieldId() + " does not belong to this form.");
            }
            Field fieldEntity = fieldService.findFieldById(reqField.getFieldId());

            if (!fieldEntity.getType().equals(reqField.getFieldType())) {
                throw new FieldValidationException("Type of field with id "+ reqField.getFieldId()+ " is mismatch");
            }
            fieldService.validateField(reqField.getFieldType(), reqField.getValue(), Arrays.stream(fieldEntity.getOptions().split("; ")).toList());
            if (fieldEntity.getRequired() && (reqField.getValue() == null || reqField.getValue().isEmpty())){
                throw new FieldValidationException("Field value cannot be empty when required");
            }
                submissionValues.add(submissionValueRepository.save(SubmissionValue.builder()
                        .submission(submission)
                        .value(reqField.getValue())
                        .field(fieldEntity)
                        .build())
                );

        }
        submission.setSubmissionValues(submissionValues);
        return submissionMapper.toResponseSubmitDTO(submissionRepository.save(submission));
    }

    public ResponsePaginationDTO getSubmittedForm(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Submission> submissionPage = submissionRepository.findAll(pageable);

        return ResponsePaginationDTO.builder()
                .meta(ResponsePaginationDTO.Meta.builder()
                        .page(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .total(submissionPage.getTotalPages())
                        .pages(submissionPage.getTotalPages())
                        .build())
                .result(submissionPage.getContent().stream()
                        .map(submissionMapper::toResponseSubmitDTO)
                        .toList())
                .build();
    }
}
