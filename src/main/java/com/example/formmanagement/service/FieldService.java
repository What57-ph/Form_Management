package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import com.example.formmanagement.mapper.FieldMapper;
import com.example.formmanagement.repository.FieldRepository;
import com.example.formmanagement.utils.exception.ExistException;
import com.example.formmanagement.utils.exception.FieldValidationException;
import com.example.formmanagement.utils.exception.NotFoundException;
import com.example.formmanagement.utils.validation.FieldValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FieldService {
    FieldRepository fieldRepository;
    FieldMapper fieldMapper;

    public void validateField(Field field, String value) throws FieldValidationException {
        switch (field.getType()){
            case TEXT -> FieldValidator.validateText(value);
            case DATE -> FieldValidator.validateDate(value);
            case COLOR -> FieldValidator.validateColor(value);
            case NUMBER -> FieldValidator.validateNumber(value);
            case SELECT -> FieldValidator.validateSelect(field, value);
            default -> throw new FieldValidationException("Invalid Field.");
        }
    }

    public List<ResponseFieldDTO> getAllResponse(){
        List<Field> fields = fieldRepository.findAll();
        return fields.stream().map(field -> fieldMapper.toResponseField(field))
                .collect(Collectors.toList());
    }

    public ResponseFieldDTO getSingleResponse(Long id){
        Field field = fieldRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cannot find Field.")
        );
        return fieldMapper.toResponseField(field);
    }

    public void addField(RequestFieldDTO request){
        Optional<Field> optField = fieldRepository.findByLabel(request.getLabel());
        if (optField.isPresent()){
            throw new ExistException("Field already exist.");
        }
        Field field = null;
        if (request.getOrder() != null && request.getRequired() != null && request.getType() != null) {
            field = Field.builder()
                    .label(request.getLabel())
                    .type(request.getType())
                    .required(request.getRequired())
                    .order(String.join(", ", request.getOrder()))
                    .build();
        } else {
            throw new RuntimeException("All fields should be filled.");
        }
        if (field != null) {
            if (Boolean.TRUE.equals(request.getRequired()) && request.getOptions() != null){

                field.setOptions(String.join(", ", request.getOptions()));

            }
            fieldRepository.save(field);
        }
    }

    public void deleteField(Long id){
       fieldRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Cannot find field.")
       );
       fieldRepository.deleteById(id);
    }

    public void updateField(RequestFieldDTO request){
        if (request.getId() == null) {
            throw new RuntimeException("ID must not be null.");
        }
        Field field = fieldRepository.findById(request.getId()).orElseThrow(
                () -> new NotFoundException("Cannot find field.")
        );
        if (request.getOptions() != null) {
            field.setOptions(String.join(", ", request.getOptions()));
        }
        if (request.getOrder() != null){
            field.setOrder(String.join(", ", request.getOrder()));
        }
        if (request.getLabel() != null){
            field.setLabel(request.getLabel());
        }
        if (request.getRequired() != null){
            field.setRequired(request.getRequired());
        }
        if (request.getType() != null){
            field.setType(request.getType());
        }
        fieldRepository.save(field);
    }
}
