package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import com.example.formmanagement.mapper.FieldMapper;
import com.example.formmanagement.repository.FieldRepository;
import com.example.formmanagement.repository.FormRepository;
import com.example.formmanagement.utils.enums.FieldType;
import com.example.formmanagement.utils.exception.ExistException;
import com.example.formmanagement.utils.exception.FieldValidationException;
import com.example.formmanagement.utils.exception.NotFoundException;
import com.example.formmanagement.utils.exception.RequestInvalidException;
import com.example.formmanagement.utils.validation.FieldValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FieldService {
    FieldRepository fieldRepository;
    FormRepository formRepository;
    FieldMapper fieldMapper;

    public void validateField(FieldType fieldType, String value, List<String> options) throws FieldValidationException {
        switch (fieldType) {
            case TEXT -> FieldValidator.validateText(value);
            case DATE -> FieldValidator.validateDate(value);
            case COLOR -> FieldValidator.validateColor(value);
            case NUMBER -> FieldValidator.validateNumber(value);
            case SELECT -> {
                if (options.isEmpty()) {
                    throw new FieldValidationException("Options cannot be empty.");
                }
                FieldValidator.validateSelect(options, value);
            }
            default -> throw new FieldValidationException("Invalid Field.");
        }
    }

    public Field findFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId).orElseThrow(
                () -> new NotFoundException("Cannot find Field.")
        );
    }

    public List<ResponseFieldDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        return fields.stream().map(fieldMapper::toResponseField)
                .collect(Collectors.toList());
    }

    public ResponseFieldDTO getSingleField(Long id) {
        Field field = findFieldById(id);
        return fieldMapper.toResponseField(field);
    }

    public void setFieldToForm(Long formId, Long fieldId) {
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );
        Field field = findFieldById(fieldId);
        field.setForm(form);
        fieldRepository.save(field);
    }

    public Field addField(RequestFieldDTO request) {
//        Optional<Field> optField = fieldRepository.findByLabel(request.getLabel());
//        if (optField.isPresent()){
//            throw new ExistException("Field already exist.");
//        }
        Field field = null;
        if (request.getLabel() != null && request.getOrder() != null && request.getRequired() != null && request.getType() != null) {
            field = Field.builder()
                    .label(request.getLabel())
                    .type(request.getType())
                    .required(request.getRequired())
                    .order(String.join("; ", request.getOrder()))
                    .build();
        } else {
            throw new RequestInvalidException("All fields should be filled.");
        }

        if (request.getType().equals(FieldType.SELECT) && request.getOptions() != null) {
            field.setOptions(String.join("; ", request.getOptions()));
        }
        return fieldRepository.save(field);

    }

    public void deleteField(Long id) {
        findFieldById(id);
        fieldRepository.deleteById(id);
    }

    public Field updateField(RequestFieldDTO request, Long id) {
        Field field = findFieldById(id);
        if (request.getOptions() != null) {
            field.setOptions(String.join("; ", request.getOptions()));
        }
        if (request.getOrder() != null) {
            field.setOrder(String.join("; ", request.getOrder()));
        }
        if (request.getLabel() != null) {
            field.setLabel(request.getLabel());
        }
        if (request.getRequired() != null) {
            field.setRequired(request.getRequired());
        }
        if (request.getType() != null) {
            field.setType(request.getType());
        }
        return fieldRepository.save(field);
    }
}
