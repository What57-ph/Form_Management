package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.domain.response.ResponseFormDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormMapper {

    ModelMapper modelMapper;
    FieldMapper fieldMapper;

    public ResponseFormDTO toResponseFormDTO(Form form) {
        if (form == null) return null;

        ResponseFormDTO dto = modelMapper.map(form, ResponseFormDTO.class);
        dto.setFormId(form.getId());
        if (form.getOrder() != null && !form.getOrder().isEmpty()) {
            dto.setOrder(Arrays.asList(form.getOrder().split("; ")));
        } else {
            dto.setOrder(Collections.emptyList());
        }

        if (form.getFields() != null) {
            List<ResponseFieldDTO> fieldResponses = form.getFields().stream()
                    .map(fieldMapper::toResponseField)
                    .collect(Collectors.toList());
            dto.setFields(fieldResponses);
        }

        return dto;
    }
}