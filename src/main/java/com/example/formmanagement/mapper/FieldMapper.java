package com.example.formmanagement.mapper;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.domain.response.ResponseField;
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

        TypeMap<Field, ResponseField> propertyMapper = this.modelMapper.createTypeMap(Field.class, ResponseField.class);

        Converter<String, List<String>> stringToListConverter = context ->
                context.getSource() != null
                        ? Arrays.asList(context.getSource().split(", "))
                        : Collections.emptyList();

        propertyMapper.addMappings(mapper ->
                mapper.using(stringToListConverter).map(Field::getOrder, ResponseField::setOrder)
        );
    }

    public ResponseField toResponseField(Field field){
        if (field == null) return null;
        return modelMapper.map(field, ResponseField.class);
    }
}
