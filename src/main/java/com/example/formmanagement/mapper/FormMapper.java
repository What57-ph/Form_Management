package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.response.ResponseField;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormMapper {
    ModelMapper modelMapper;

    public ResponseField toResponseField(Field field){
        if (field == null) return null;
        return modelMapper.map(field, ResponseField.class);
    }
}
