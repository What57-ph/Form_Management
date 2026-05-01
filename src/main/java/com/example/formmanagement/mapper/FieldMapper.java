package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class FieldMapper {
    private final ModelMapper modelMapper;

    public FieldMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        TypeMap<Field, ResponseFieldDTO> propertyMapper = this.modelMapper.createTypeMap(Field.class, ResponseFieldDTO.class);

        Converter<String, List<String>> stringToListConverter = context ->
                context.getSource() != null
                        ? Arrays.asList(context.getSource().split(", "))
                        : Collections.emptyList();

        propertyMapper.addMappings(mapper ->
                mapper.using(stringToListConverter).map(Field::getOrder, ResponseFieldDTO::setOrder)
        );
    }

    public ResponseFieldDTO toResponseField(Field field){
        if (field == null) return null;
        ResponseFieldDTO dto = modelMapper.map(field, ResponseFieldDTO.class);
        dto.setFieldId(field.getId());
        return dto;
    }

    public Field toField(ResponseFieldDTO responseFieldDTO){
        Field field = modelMapper.map(responseFieldDTO, Field.class);
        field.setId(responseFieldDTO.getFieldId());
        if (responseFieldDTO.getOptions() != null){
            field.setOptions(String.join(", ", responseFieldDTO.getOptions()));
        }
        if (responseFieldDTO.getOrder() != null){
            field.setOrder(String.join(", ", responseFieldDTO.getOrder()));
        }
        return field;
    }
}
