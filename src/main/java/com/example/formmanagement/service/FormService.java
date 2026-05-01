package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.request.RequestFormDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import com.example.formmanagement.domain.response.ResponseFormDTO;
import com.example.formmanagement.mapper.FieldMapper;
import com.example.formmanagement.mapper.FormMapper;
import com.example.formmanagement.repository.FieldRepository;
import com.example.formmanagement.repository.FormRepository;
import com.example.formmanagement.utils.enums.FormStatus;
import com.example.formmanagement.utils.exception.NotFoundException;
import com.example.formmanagement.utils.exception.RequestInvalidException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Form form;
        if (request.getOrder() != null && request.getTitle() != null && request.getDescription() != null){
            form = Form.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .status(FormStatus.ACTIVE)
                    .order(String.join(",", request.getOrder()))
                    .build();
        } else {
            throw new RequestInvalidException("All form attributes are required.");
        }

        return formMapper.toResponseFormDTO(formRepository.save(form));
    }

    public List<ResponseFormDTO> getAllForms() {
        return formRepository.findAll().stream()
                .map(formMapper::toResponseFormDTO)
                .collect(Collectors.toList());
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

        if (request.getOrder() != null) form.setOrder(String.join(", ", request.getOrder()));

        if (request.getStatus() != null) form.setStatus(request.getStatus());

        return formMapper.toResponseFormDTO(formRepository.save(form));
    }

    @Transactional
    public void deleteForm(Long id) {
        if (!formRepository.existsById(id)) {
            throw new NotFoundException("Form not found");
        }
        formRepository.deleteById(id);
    }

    @Transactional
    public ResponseFormDTO addFieldToForm(Long formId, RequestFieldDTO requestFieldDTO){
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );
        Field field = fieldService.addField(requestFieldDTO);
        fieldService.setFieldToForm(formId, field.getId());
        return formMapper.toResponseFormDTO(form);
    }

    @Transactional
    public ResponseFieldDTO updateFieldOfForm(Long formId, Long fieldId, RequestFieldDTO requestFieldDTO){
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );
        Field field = fieldRepository.findById(fieldId).orElseThrow(
                () -> new NotFoundException("Cannot find field")
        );

        return fieldMapper.toResponseField(fieldService.updateField(requestFieldDTO, fieldId));
    }

    public void deleteFieldOfForm(Long formId, Long fieldId){
        Form form = formRepository.findById(formId).orElseThrow(
                () -> new NotFoundException("Cannot find form")
        );
        Field field = fieldRepository.findById(fieldId).orElseThrow(
                () -> new NotFoundException("Cannot find field")
        );
        fieldService.deleteField(fieldId);
    }

    public List<ResponseFormDTO> getActiveForms(){
        return formRepository.findAllByStatus(FormStatus.ACTIVE).stream().map(
                formMapper::toResponseFormDTO
        ).collect(Collectors.toList());
    }
}