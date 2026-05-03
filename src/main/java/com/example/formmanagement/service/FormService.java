package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.request.RequestFormDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import com.example.formmanagement.domain.response.ResponseFormDTO;
import com.example.formmanagement.domain.response.ResponsePaginationDTO;
import com.example.formmanagement.mapper.FieldMapper;
import com.example.formmanagement.mapper.FormMapper;
import com.example.formmanagement.repository.FieldRepository;
import com.example.formmanagement.repository.FormRepository;
import com.example.formmanagement.utils.enums.FormStatus;
import com.example.formmanagement.utils.exception.ExistException;
import com.example.formmanagement.utils.exception.NotFoundException;
import com.example.formmanagement.utils.exception.RequestInvalidException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormService {

    FormRepository formRepository;
    FormMapper formMapper;
    FieldMapper fieldMapper;
    FieldService fieldService;
    FieldRepository fieldRepository;

    @Transactional
    public ResponseFormDTO createForm(RequestFormDTO request) {
        if (formRepository.existsByTitle(request.getTitle())) {
            throw new ExistException("Form title already exist");
        }
        Form form;
        if (request.getOrder() != 0 && request.getTitle() != null && request.getDescription() != null) {
            form = Form.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .status(FormStatus.ACTIVE)
//                    .order(String.join("; ", request.getOrder()))
                    .order(request.getOrder())
                    .build();
        } else {
            throw new RequestInvalidException("All form attributes are required.");
        }

        return formMapper.toResponseFormDTO(formRepository.save(form));
    }

    public ResponsePaginationDTO getAllForms(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("order").ascending());

        Page<Form> formPage = formRepository.findAll(pageable);

        return ResponsePaginationDTO.builder()
                .meta(ResponsePaginationDTO.Meta.builder()
                        .page(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .pages(formPage.getTotalPages())
                        .total(formPage.getTotalElements())
                        .build())
                .result(formPage.getContent().stream()
                        .map(formMapper::toResponseFormDTO)
                        .toList())
                .build();
    }

    public ResponseFormDTO getFormById(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form not found with id: " + id));
        return formMapper.toResponseFormDTO(form);
    }

    @Transactional
    public ResponseFormDTO updateForm(RequestFormDTO request, Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form not found"));
        if (request.getTitle() != null) form.setTitle(request.getTitle());

        if (request.getDescription() != null) form.setDescription(request.getDescription());

//        if (request.getOrder() != null) form.setOrder(String.join("; ", request.getOrder()));
        if (request.getOrder() != null) form.setOrder(request.getOrder());

        if (request.getStatus() != null) form.setStatus(request.getStatus());

        return formMapper.toResponseFormDTO(formRepository.save(form));
    }

    @Transactional
    public void deleteForm(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form not found"));
        if (form.getSubmissions() != null && !form.getSubmissions().isEmpty()) {
            throw new RequestInvalidException("Cannot delete form that has been submitted by employee");
        }
        formRepository.deleteById(id);
    }

    @Transactional
    public List<ResponseFormDTO> addFieldsToForm(Long formId, List<RequestFieldDTO> requestFieldDTOs) {

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new NotFoundException("Cannot find form"));

        List<Field> fields = new ArrayList<>();

        for (RequestFieldDTO dto : requestFieldDTOs) {

            Field field = Field.builder()
                    .label(dto.getLabel())
                    .type(dto.getType())
                    .required(dto.getRequired())
                    .order(dto.getOrder() != null
//                            String.join("; ", dto.getOrder())
                            ? dto.getOrder()
                            : null)
                    .options(dto.getOptions() != null ? String.join("; ", dto.getOptions()) : null)
                    .form(form)
                    .build();

            fields.add(field);
        }

        fieldRepository.saveAll(fields);

        Form updatedForm = formRepository.findById(formId)
                .orElseThrow(() -> new NotFoundException("Cannot find form"));

        return List.of(formMapper.toResponseFormDTO(updatedForm));
    }

    @Transactional
    public ResponseFieldDTO updateFieldOfForm(Long formId, Long fieldId, RequestFieldDTO requestFieldDTO) {
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );
        Field field = fieldService.findFieldById(fieldId);
        if (!field.getForm().getId().equals(formId)) {
            throw new RequestInvalidException("Field with id " + fieldId + " does not belong to this form");
        }
        return fieldMapper.toResponseField(fieldService.updateField(requestFieldDTO, fieldId));
    }

    public void deleteFieldOfForm(Long formId, Long fieldId) {
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );

        Field field = fieldService.findFieldById(fieldId);

        if (!field.getForm().getId().equals(formId)) {
            throw new RequestInvalidException("Field with id " + fieldId + " does not belong to this form");
        }

        if (field.getSubmissionValues() != null && !field.getSubmissionValues().isEmpty()) {
            throw new RequestInvalidException("Cannot delete field that has data submitted by employee");
        }

        fieldService.deleteField(fieldId);
    }

    public ResponsePaginationDTO getActiveForms(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("order").ascending());
        Page<Form> formPage = formRepository.findAllByStatus(FormStatus.ACTIVE, pageable);

        return ResponsePaginationDTO.builder()
                .meta(ResponsePaginationDTO.Meta.builder()
                        .page(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .pages(formPage.getTotalPages())
                        .total(formPage.getTotalElements())
                        .build())
                .result(formPage.getContent().stream()
                        .map(formMapper::toResponseFormDTO)
                        .toList())
                .build();
    }
}