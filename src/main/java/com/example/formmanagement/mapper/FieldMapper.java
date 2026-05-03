package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldMapper {
    private final ModelMapper modelMapper;


    public ResponseFieldDTO toResponseField(Field field){
        if (field == null) return null;
        ResponseFieldDTO dto = modelMapper.map(field, ResponseFieldDTO.class);
        if (field.getOptions() != null){
            dto.setOptions(Arrays.stream(field.getOptions().split("; ")).toList());
        }
        dto.setFieldId(field.getId());
        return dto;
    }

    public Field toField(ResponseFieldDTO responseFieldDTO){
        Field field = modelMapper.map(responseFieldDTO, Field.class);
        field.setId(responseFieldDTO.getFieldId());
        if (responseFieldDTO.getOptions() != null){
            field.setOptions(String.join("; ", responseFieldDTO.getOptions()));
        }
        return field;
    }
}
